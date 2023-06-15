package com.example.sobesgbusmmap.view.markerList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sobesgbusmmap.IonBackPressed
import com.example.sobesgbusmmap.databinding.MarkerListFragmentBinding
import com.example.sobesgbusmmap.model.Dependencies
import com.example.sobesgbusmmap.model.room.MarkerData
import com.example.sobesgbusmmap.view.map.IRefreshMarkers
import com.example.sobesgbusmmap.viewModel.markerList.MarkerListViewModel

class MarkerListFragment(private val callbackRefreshMarkers: IRefreshMarkers) : Fragment(), IonBackPressed {

    private var _binding: MarkerListFragmentBinding? = null
    private val binding: MarkerListFragmentBinding
        get() {
            return _binding!!
        }

    private val viewModel by lazy { MarkerListViewModel(Dependencies.markersRepository) }

    private lateinit var markersAdapter: MarkerListAdapter

    private val callbackDeleteItem = object : IDeleteMarker {
        override fun delete(id: Long, position: Int) {
            viewModel.deleteMarkerById(id)
            markersAdapter.setListDataDelete(position)
        }
    }

    private val callbackSaveItemChanges = object : ISaveMarkerChanges {
        override fun saveChanges(markerData: MarkerData, position: Int) {
            viewModel.saveMarkerChanges(markerData)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Dependencies.init(requireContext())
        setHasOptionsMenu(true)
        _binding = MarkerListFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.markerListRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        initMarkerList()
    }

    private fun initMarkerList() {
        viewModel.getAllMarkers().observe(viewLifecycleOwner) {
            markersAdapter = MarkerListAdapter(it, callbackDeleteItem, callbackSaveItemChanges)
            binding.markerListRecyclerView.adapter = markersAdapter
        }
    }

    override fun onBackPressed(): Boolean {
        callbackRefreshMarkers.refreshMarkers()
        Toast.makeText(requireContext(), "refreshing markers", Toast.LENGTH_SHORT).show()
        return true
    }
}