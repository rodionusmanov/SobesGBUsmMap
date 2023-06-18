package com.example.sobesgbusmmap.filmApp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sobesgbusmmap.databinding.FilmMainFragmentBinding
import com.example.sobesgbusmmap.filmApp.viewModel.FilmsFragmentAppState
import com.example.sobesgbusmmap.filmApp.viewModel.FilmsViewModel

class FilmListFragment : Fragment() {

    private var _binding: FilmMainFragmentBinding? = null
    private val binding: FilmMainFragmentBinding
        get() {
            return _binding!!
        }
    private lateinit var filmListAdapter: FilmListAdapter
    private val viewModel by lazy { FilmsViewModel() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FilmMainFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.filmsLiveData.observe(viewLifecycleOwner, { renderData(it) })
        viewModel.getTopMoviesList()
        binding.filmListRecyclerview.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun renderData(it: FilmsFragmentAppState) {
        when (it) {
            is FilmsFragmentAppState.Error -> {Toast.makeText(requireContext(), "${it.error}", Toast.LENGTH_LONG).show()}
            FilmsFragmentAppState.Loading -> {Toast.makeText(requireContext(), "Loading", Toast.LENGTH_LONG).show()}
            is FilmsFragmentAppState.Success -> {
                Toast.makeText(requireContext(), "Success", Toast.LENGTH_LONG).show()
                with(binding) {
                    filmListAdapter = FilmListAdapter(it.movieList)
                    filmListRecyclerview.adapter = filmListAdapter
                    Toast.makeText(requireContext(), "size ${it.movieList.size}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

}