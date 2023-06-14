package com.example.sobesgbusmmap.view.markerList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sobesgbusmmap.databinding.MarkerListFragmentBinding
import com.example.sobesgbusmmap.model.Dependencies
import com.example.sobesgbusmmap.viewModel.markerList.MarkerListViewModel

class MarkerListFragment : Fragment(){

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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
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
            markersAdapter = MarkerListAdapter(it, callbackDeleteItem)
            binding.markerListRecyclerView.adapter = markersAdapter
        }
    }
}