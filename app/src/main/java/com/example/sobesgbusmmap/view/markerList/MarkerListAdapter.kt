package com.example.sobesgbusmmap.view.markerList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sobesgbusmmap.databinding.MarkerListItemBinding
import com.example.sobesgbusmmap.model.room.MarkerData

class MarkerListAdapter(
    private val markerList: List<MarkerData>,
    val callbackDeleteItem: IDeleteMarker
) : RecyclerView.Adapter<MarkerListAdapter.MarkerListViewHolder>() {

    val mutableMarkerList: MutableList<MarkerData> = markerList.toMutableList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarkerListViewHolder {

        val binding =
            MarkerListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MarkerListViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: MarkerListViewHolder, position: Int) {
        holder.bind(mutableMarkerList[position])
    }

    override fun getItemCount(): Int {
        return mutableMarkerList.size
    }

    fun setListDataDelete(position: Int) {
        notifyItemRemoved(position)
        mutableMarkerList.removeAt(position)
    }

    inner class MarkerListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(marker: MarkerData) {
            MarkerListItemBinding.bind(itemView).apply {
                markerName.text = marker.markerName
                markerCoordinates.text =
                    "lat: ${marker.markerCoordinatesLat} '/n' lon: ${marker.markerCoordinatesLng}"
                markerAnnotation.setText(marker.markerAnnotation)
                deleteMarkerButton.setOnClickListener {
                    callbackDeleteItem.delete(marker.markerId, layoutPosition)
                }
                /*root.setOnClickListener {
                    callback.onItemClick(weather)
                }*/
            }
        }
    }
}