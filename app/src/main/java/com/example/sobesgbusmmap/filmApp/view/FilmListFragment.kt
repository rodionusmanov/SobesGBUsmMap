package com.example.sobesgbusmmap.filmApp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sobesgbusmmap.R
import com.example.sobesgbusmmap.databinding.FilmMainFragmentBinding
import com.example.sobesgbusmmap.filmApp.model.Movie
import com.example.sobesgbusmmap.filmApp.utils.PaginationScrollListener
import com.example.sobesgbusmmap.filmApp.viewModel.FilmsFragmentAppState
import com.example.sobesgbusmmap.filmApp.viewModel.FilmsViewModel

class FilmListFragment : Fragment() {

    private val pageStart: Int = 0
    private var isLoading: Boolean = false
    private var isLastPage: Boolean = false
    private var totalPages: Int = 249
    private var currentPage: Int = pageStart
    var movieListToAdapter: MutableList<Movie> = arrayListOf()

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

        _binding = FilmMainFragmentBinding.inflate(inflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.filmsLiveData.observe(viewLifecycleOwner, { renderData(it) })
        viewModel.getTopMoviesList()
    }

    private fun renderData(it: FilmsFragmentAppState) {
        when (it) {
            is FilmsFragmentAppState.Error -> {
            }

            FilmsFragmentAppState.Loading -> {
            }

            is FilmsFragmentAppState.Success -> {
                with(binding) {
                    filmListRecyclerview.layoutManager =
                        LinearLayoutManager(requireContext())

                    filmListAdapter = FilmListAdapter(this@FilmListFragment, callbackOpenFilmInfo)
                    filmListAdapter.add(it.movieList[pageStart])
                    filmListRecyclerview.adapter = filmListAdapter

                    filmListRecyclerview.addOnScrollListener(object :
                        PaginationScrollListener(binding.filmListRecyclerview.layoutManager as LinearLayoutManager) {
                        override fun loadMoreItems() {
                            currentPage += 1
                            filmListAdapter.add(it.movieList[currentPage])

                        }

                        override fun getTotalPageCount(): Int {
                            return totalPages
                        }

                        override fun isLastPage(): Boolean {
                            if (currentPage == getTotalPageCount()) {
                                return !isLastPage
                            }
                            return isLastPage
                        }

                        override fun isLoading(): Boolean {
                            return isLoading
                        }
                    })

                }
            }
        }
    }

    fun loadNextPage() {
    }
}