package com.example.sobesgbusmmap.pulseApp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sobesgbusmmap.databinding.PulseFragmentBinding
import com.example.sobesgbusmmap.pulseApp.data.PulseData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext


class PulseFragment : Fragment() {
    private lateinit var binding: PulseFragmentBinding
    private val pulseAdapter = PulseItemAdapter()
    private lateinit var pulseList: MutableList<PulseData>
    private val pulseCollection = FirebaseFirestore.getInstance().collection("pulse")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = PulseFragmentBinding.inflate(inflater)

        getDataFromFirestore()
        return binding.root
    }

    private fun getDataFromFirestore() {
        GlobalScope.launch(Dispatchers.IO) {
            pulseList = pulseCollection.orderBy("date", Query.Direction.DESCENDING).get().await().toObjects(PulseData::class.java)
            withContext(Dispatchers.Main) {
                initRecyclerView(pulseList)
            }
        }
    }

    private fun initRecyclerView(pulseList: MutableList<PulseData>) {
        pulseAdapter.setDataSource(pulseList)
        with(binding.mainRecyclerView) {
            layoutManager = LinearLayoutManager(context)
            adapter = pulseAdapter
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fab.setOnClickListener {
            val dialog = AddPulseDialogFragment(pulseList)
            dialog.show(childFragmentManager, "adding pulse data")
        }
    }
}