package com.example.sobesgbusmmap.filmApp.view

import android.app.ActionBar.LayoutParams
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sobesgbusmmap.R
import com.example.sobesgbusmmap.databinding.FilmMainFragmentBinding
import com.example.sobesgbusmmap.filmApp.model.Movie
import com.example.sobesgbusmmap.filmApp.viewModel.FilmsFragmentAppState
import com.example.sobesgbusmmap.filmApp.viewModel.FilmsViewModel
import com.example.sobesgbusmmap.mapApp.view.markerList.IDeleteMarker

class FilmListFragment : Fragment() {

    private var _binding: FilmMainFragmentBinding? = null
    private val binding: FilmMainFragmentBinding
        get() {
            return _binding!!
        }
    private lateinit var filmListAdapter: FilmListAdapter
    private val viewModel by lazy { FilmsViewModel() }

    private val callbackOpenFilmInfo = object : IOpenFilmInfo {
        override fun openFilmInfo(movie: Movie) {
            requireActivity().supportFragmentManager
                .beginTransaction()
                .hide(this@FilmListFragment)
                .add(R.id.container, FilmInfoFragment(movie))
                .addToBackStack(null)
                .commit()
        }
    }

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
        binding.filmListRecyclerview.apply {
            layoutManager = GridLayoutManager(requireContext(),2)
        }
    }

    private fun renderData(it: FilmsFragmentAppState) {
        when (it) {
            is FilmsFragmentAppState.Error -> {
            }

            FilmsFragmentAppState.Loading -> {
            }

            is FilmsFragmentAppState.Success -> {
                with(binding) {
                    filmListAdapter = FilmListAdapter(it.movieList, callbackOpenFilmInfo)
                    filmListRecyclerview.adapter = filmListAdapter
                }
            }
        }
    }
}