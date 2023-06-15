package com.example.sobesgbusmmap.viewModel.markerList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sobesgbusmmap.model.repository.MarkersRepositoryImpl
import com.example.sobesgbusmmap.model.room.MarkerData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class MarkerListViewModel(private val markersRepository: MarkersRepositoryImpl) : ViewModel() {

    private val viewModelCoroutineScope = CoroutineScope(
        Dispatchers.Main
                + SupervisorJob()
    )

    fun getAllMarkers(): LiveData<List<MarkerData>> {
        val result = MutableLiveData<List<MarkerData>>()
        viewModelCoroutineScope.launch {
            val returnedData = markersRepository.getAllMarkersData()
            result.postValue(returnedData)
        }
        return result
    }

    fun deleteMarkerById(id: Long) {
        viewModelCoroutineScope.launch {
            markersRepository.removeMarkerFromData(id)
        }
    }

    fun saveMarkerChanges(
        newMarkerData: MarkerData
    ) {
        viewModelCoroutineScope.launch {
            markersRepository.removeMarkerFromData(newMarkerData.markerId)
            markersRepository.insertNewMarkerToData(newMarkerData)
        }
    }
}
