package com.example.sobesgbusmmap.view.map

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Criteria
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.sobesgbusmmap.R
import com.example.sobesgbusmmap.databinding.MapFragmentBinding
import com.example.sobesgbusmmap.model.Dependencies
import com.example.sobesgbusmmap.model.room.MarkerData
import com.example.sobesgbusmmap.view.markerList.MarkerListFragment
import com.example.sobesgbusmmap.viewModel.map.MapViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.snackbar.Snackbar
import java.time.LocalDate
import java.time.LocalDateTime


class MapFragment : Fragment() {

    private val viewModel by lazy { MapViewModel(Dependencies.markersRepository) }

    private lateinit var map: GoogleMap

    private var markersList: List<MarkerData> = emptyList()

    private val callback = OnMapReadyCallback { googleMap ->
        map = googleMap
        map.setOnMapLongClickListener {
            setMarker(it, "click marker ${LocalDateTime.now()}")
            insertMarkerFromClick(
                (it.latitude + it.longitude).toLong(),
                it.latitude,
                it.longitude,
                "click marker ${LocalDateTime.now()}"
            )
        }
//        val sydney = LatLng(-34.0, 151.0)
//        googleMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
//        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
        googleMap.uiSettings.isZoomControlsEnabled = true
    }

    private var _binding: MapFragmentBinding? = null
    private val binding: MapFragmentBinding
        get() {
            return _binding!!
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Dependencies.init(requireContext())
        setHasOptionsMenu(true)
        _binding = MapFragmentBinding.inflate(inflater)
        initMap()
        return binding.root
    }

    private fun initMarkerList() {
        viewModel.getAllMarkers().observe(viewLifecycleOwner, Observer { returnedData ->
            markersList = returnedData
            if (markersList.isNotEmpty()) {
                markersList.forEach {
                    setMarker(
                        LatLng(it.markerCoordinatesLat, it.markerCoordinatesLng),
                        it.markerName
                    )
                }
            }
        })
    }

    private fun initMap() {
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.google_map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
        initMarkerList()
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.center_geoposition -> {
            if (checkLocationPermission()) {
                map.moveCamera(CameraUpdateFactory.newLatLng(getLocation()))
            } else {
                showSnackbarNoGPSPermission()
            }
            true
        }

        R.id.set_marker_on_device_location -> {
            if (checkLocationPermission()) {
                setMarker(getLocation(), "Device location ${LocalDate.now().toString()}")
                insertMarkerFromClick(
                    (getLocation().latitude + getLocation().longitude).toLong(),
                    getLocation().latitude,
                    getLocation().longitude,
                    "Device location ${LocalDate.now().toString()}"
                )
            } else {
                showSnackbarNoGPSPermission()
            }
            true
        }

        R.id.item_marker_list -> {
            requireActivity()
                .supportFragmentManager
                .beginTransaction()
                .hide(this)
                .add(R.id.container, MarkerListFragment())
                .addToBackStack(null)
                .commit()
            true
        }

        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    private fun showSnackbarNoGPSPermission() {
        Snackbar.make(
            requireView(),
            "не предоставлен доступ к GPS",
            Snackbar.LENGTH_SHORT
        ).show()
    }

    private fun checkLocationPermission(): Boolean {
        val permissionResult = ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION
        )
        return (permissionResult == PackageManager.PERMISSION_GRANTED)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    }

    private fun checkPermission(permission: String) {
        val permResult = ContextCompat.checkSelfPermission(
            requireContext(),
            permission
        )
        PackageManager.PERMISSION_GRANTED
        if (permResult == PackageManager.PERMISSION_GRANTED) {
        } else if (shouldShowRequestPermissionRationale(permission)) {
            AlertDialog.Builder(requireContext())
                .setTitle("Доступ к геоданным")
                .setMessage("Запрос на доступ к геоданным. В случае отказа, доступ можно будет предоставить только в настройках приложения.")
                .setPositiveButton("Открыть окно предоставления доступа") { _, _ ->
                    permissionRequest(permission)
                }
                .setNegativeButton("Отказать в запросе") { dialog, _ -> dialog.dismiss() }
                .create()
                .show()
        } else {
            permissionRequest(permission)
        }
    }

    private fun setMarker(
        location: LatLng,
        searchString: String
    ): Marker? {
        return map.addMarker(
            MarkerOptions()
                .position(location)
                .title(searchString)
        )
    }

    private fun getLocation(): LatLng {
        val locationManager =
            requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        var lastKnownLocation =
            locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER)
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {

            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                val criteria = Criteria()
                criteria.accuracy = Criteria.ACCURACY_FINE
                locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    0L,
                    1000F,
                    locationListener
                )
                lastKnownLocation =
                    locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            } else {
                locationListener.onProviderDisabled("")
            }
        }
        return if (lastKnownLocation != null) {
            LatLng(lastKnownLocation.latitude, lastKnownLocation.longitude)
        } else {
            LatLng(0.0, 0.0)
        }
    }

    private val locationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
        }

        override fun onProviderDisabled(provider: String) {
            Snackbar.make(
                requireView(),
                "GPS отключен",
                Snackbar.LENGTH_SHORT
            ).show()
            super.onProviderDisabled(provider)
        }

        override fun onProviderEnabled(provider: String) {
            Snackbar.make(
                requireView(),
                "GPS включен",
                Snackbar.LENGTH_SHORT
            ).show()
            super.onProviderEnabled(provider)
        }
    }

    private val REQUEST_CODE_LOCATION = 666

    private fun permissionRequest(permission: String) {
        requestPermissions(arrayOf(permission), REQUEST_CODE_LOCATION)
    }

    private fun insertMarkerFromClick(id: Long, lat: Double, lng: Double, name: String) {
        viewModel.insertNewMarker(
            id,
            name,
            lat,
            lng,
            ""
        )
    }
}