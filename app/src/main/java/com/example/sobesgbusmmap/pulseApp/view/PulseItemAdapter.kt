package com.example.sobesgbusmmap.pulseApp.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sobesgbusmmap.databinding.PulseItemBinding
import com.example.sobesgbusmmap.pulseApp.data.PulseData

class PulseItemAdapter : RecyclerView.Adapter<PulseItemAdapter.PulseViewHolder>() {

    private lateinit var currentDataSource: MutableList<PulseData>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PulseViewHolder {
        val binding = PulseItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PulseViewHolder(binding.root)
    }

    override fun getItemCount() = currentDataSource.size

    override fun onBindViewHolder(holder: PulseViewHolder, position: Int) {
        holder.bind(currentDataSource[position])
    }

    fun setDataSource(dataList: MutableList<PulseData>) {
        currentDataSource = dataList
        notifyDataSetChanged()
    }

    inner class PulseViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(pulse: PulseData) {
            PulseItemBinding.bind(itemView).apply {
                tvDate.text = pulse.date
                tvMorningPressure.text = "${pulse.pressureUpMorning}  /  ${pulse.pressureDownMorning}"
                tvEveningPressure.text = "${pulse.pressureUpEvening}  /  ${pulse.pressureDownEvening}"
                tvMorningPulse.text = pulse.pulseMorning
                tvEveningPulse.text = pulse.pulseEvening
                tvMorningTime.text = pulse.timeMorning
                tvEveningTime.text = pulse.timeEvening
            }
        }
    }
}