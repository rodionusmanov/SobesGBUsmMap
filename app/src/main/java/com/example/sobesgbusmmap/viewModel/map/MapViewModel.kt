package com.example.sobesgbusmmap.viewModel.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sobesgbusmmap.model.repository.MarkersRepositoryImpl
import com.example.sobesgbusmmap.model.room.MarkerData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class MapViewModel(private val markersRepository: MarkersRepositoryImpl) : ViewModel() {

    private val viewModelCoroutineScope = CoroutineScope(
        Dispatchers.Main
                + SupervisorJob()
    )

    fun insertNewMarker(
        id: Long,
        name: String,
        lat: Double,
        lng: Double,
        anno: String
    ) {
        val newMarker = MarkerData(id, name, lat, lng, anno)
        viewModelCoroutineScope.launch {
            markersRepository.insertNewMarkerToData(newMarker)
        }

    }

    fun getAllMarkers(): LiveData<List<MarkerData>> {
        val result = MutableLiveData<List<MarkerData>>()
        viewModelCoroutineScope.launch {
            val returnedData = markersRepository.getAllMarkersData()
            result.postValue(returnedData)
        }
        return result
    }

    fun checkMarkerWithAskedId(id: Long): LiveData<Boolean> {
        val isExisted = MutableLiveData<Boolean>()
        viewModelCoroutineScope.launch {
            var existed = false
            val returnedData = markersRepository.getAllMarkersData()
            returnedData.forEach {
                if (it.markerId == id) {
                    existed = true
                }
            }
            isExisted.postValue(existed)
        }
        return isExisted
    }
}
