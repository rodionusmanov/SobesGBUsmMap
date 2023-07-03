package com.example.sobesgbusmmap.pulseApp.view

import android.app.ActionBar.LayoutParams
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.sobesgbusmmap.R
import com.example.sobesgbusmmap.databinding.PulseAddDialogFragmentBinding
import com.example.sobesgbusmmap.pulseApp.data.PulseData
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class AddPulseDialogFragment(private var pulseList: MutableList<PulseData>) : DialogFragment() {
    private lateinit var binding: PulseAddDialogFragmentBinding

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = PulseAddDialogFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    private fun dateAlreadyExists(): Boolean {
        pulseList.forEach {
            if (it.date == binding.tietDate.text.toString()) {
                return true
            }
        }
        return false
    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val formatterTime = DateTimeFormatter.ofPattern("HH:mm")
        val formatterDate = DateTimeFormatter.ofPattern("dd-MM-yyyy")
        binding.tietDate.setText(LocalDate.now().format(formatterDate))
        binding.tietTime.setText(LocalDateTime.now().format(formatterTime))
        if (LocalDateTime.now().hour < 14) {
            binding.llBackground.background =
                resources.getDrawable(R.drawable.morning_background, null)
        } else {
            binding.llBackground.background =
                resources.getDrawable(R.drawable.evening_background, null)
        }

        binding.chipSave.setOnClickListener {
            if (isAllFieldsFilled()) {
                Toast.makeText(context, "заполните все поля", Toast.LENGTH_SHORT).show()
            } else {
                GlobalScope.launch(Dispatchers.IO) {
                    if (dateAlreadyExists()) {
                        addOrReplacePulse()
                    } else {
                        addPulse()
                    }
                    withContext(Dispatchers.Main) {
                        dismiss()
                        requireActivity().recreate()
                    }
                }
            }
        }
    }

    private fun addPulse() {
        val pulseToAdd = if (LocalDateTime.now().hour < 13) {
            morningPulseData()
        } else {
            eveningPulseData()
        }
        addPulseToDataBase(pulseToAdd)

    }

    private fun addPulseToDataBase(pulse: PulseData) {
        GlobalScope.launch(Dispatchers.IO) {
            FirebaseFirestore.getInstance().collection("pulse")
                .document(binding.tietDate.text.toString())
                .set(pulse).await()
        }
    }

    private fun morningPulseData() =
        PulseData(
            binding.tietDate.text.toString(),
            binding.tietPulse.text.toString(),
            binding.tietMax.text.toString(),
            binding.tietMin.text.toString(),
            binding.tietTime.text.toString(),
            "",
            "",
            "",
            ""
        )

    private fun eveningPulseData() =
        PulseData(
            binding.tietDate.text.toString(),
            "",
            "",
            "",
            "",
            binding.tietPulse.text.toString(),
            binding.tietMax.text.toString(),
            binding.tietMin.text.toString(),
            binding.tietTime.text.toString()
        )


    private fun addOrReplacePulse() {
        GlobalScope.launch(Dispatchers.IO) {
            val pulseToChange = FirebaseFirestore.getInstance().collection("pulse")
                .document(binding.tietDate.text.toString()).get().await()
                .toObject(PulseData::class.java)

            if (LocalDateTime.now().hour < 13) {
                replaceMorningData(pulseToChange)
            } else {
                replaceEveningData(pulseToChange)
            }
            if (pulseToChange != null) {
                addPulseToDataBase(pulseToChange)
            }
        }
    }

    private fun replaceEveningData(pulseToChange: PulseData?) {
        with(pulseToChange!!) {
            timeEvening = binding.tietTime.text.toString()
            pulseEvening = binding.tietPulse.text.toString()
            pressureUpEvening = binding.tietMax.text.toString()
            pressureDownEvening = binding.tietMin.text.toString()
        }
    }

    private fun replaceMorningData(pulseToChange: PulseData?) {
        with(pulseToChange!!) {
            timeMorning = binding.tietTime.text.toString()
            pulseMorning = binding.tietPulse.text.toString()
            pressureUpMorning = binding.tietMax.text.toString()
            pressureDownMorning = binding.tietMin.text.toString()
        }
    }

    private fun isAllFieldsFilled() = (
            binding.tietMax.text.toString() == "" ||
                    binding.tietMin.text.toString() == "" ||
                    binding.tietPulse.text.toString() == ""
            )
}