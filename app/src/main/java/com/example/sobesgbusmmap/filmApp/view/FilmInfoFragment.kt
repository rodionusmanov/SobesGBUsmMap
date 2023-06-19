package com.example.sobesgbusmmap.filmApp.view

import android.app.ActionBar.LayoutParams
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.size
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import coil.load
import com.example.sobesgbusmmap.databinding.FilmInfoFragmentBinding
import com.example.sobesgbusmmap.filmApp.model.Movie
import com.example.sobesgbusmmap.filmApp.utils.convertRatingToColor

class FilmInfoFragment(private val movie: Movie) : Fragment() {

    private var _binding: FilmInfoFragmentBinding? = null
    private val binding: FilmInfoFragmentBinding
        get() {
            return _binding!!
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FilmInfoFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.root.apply {
            layoutParams.width = LayoutParams.MATCH_PARENT
        }
        renderData(movie)
    }

    private fun renderData(movie: Movie) {
        with(binding) {
            ivImage.load(movie.image) {
                crossfade(500)
                placeholder(android.R.drawable.ic_menu_report_image)
            }
            tvRating.text = movie.imdbRating
            cvRating.setCardBackgroundColor(convertRatingToColor(movie.imdbRating))
            tvFullTitle.text = movie.fullTitle
            tvYear.text = movie.year
            cvYear.setCardBackgroundColor(convertRatingToColor(movie.imdbRating))
            tvCrew.text = movie.crew
            tvRatingCount.text = "IMDB Rating Count: ${movie.imDbRatingCount}"
        }
    }
}