package com.example.sobesgbusmmap.filmApp.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.Precision
import coil.size.Scale
import coil.size.Size
import coil.transform.CircleCropTransformation
import com.bumptech.glide.Glide
import com.example.sobesgbusmmap.R
import com.example.sobesgbusmmap.databinding.FilmItemBinding
import com.example.sobesgbusmmap.filmApp.model.Movie
import com.example.sobesgbusmmap.filmApp.utils.convertRatingToColor

class FilmListAdapter(
    private val movieList: List<Movie>,
    val callbackOpenFilmInfo: IOpenFilmInfo
) :
    RecyclerView.Adapter<FilmListAdapter.FilmViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FilmListAdapter.FilmViewHolder {
        val binding = FilmItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FilmViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: FilmListAdapter.FilmViewHolder, position: Int) {
        holder.bind(movieList[position])
    }

    override fun getItemCount() = movieList.size

    inner class FilmViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(movie: Movie) {
            FilmItemBinding.bind(itemView).apply {
                tvTitle.text = movie.title
                tvRating.text = movie.imdbRating
                cvRating.setCardBackgroundColor(convertRatingToColor(movie.imdbRating))
                ivImage.load(movie.image) {
                    crossfade(500)
                    scale(Scale.FILL)
                    placeholder(android.R.drawable.ic_menu_report_image)
                }
                itemView.setOnClickListener {
                    callbackOpenFilmInfo.openFilmInfo(movie)
                }
            }
        }
    }
}