package com.example.sobesgbusmmap.filmApp.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sobesgbusmmap.databinding.FilmItemBinding
import com.example.sobesgbusmmap.filmApp.model.Movie

class FilmListAdapter(private val movieList: List<Movie>) :
    RecyclerView.Adapter<FilmListAdapter.FilmViewHolder>() {

//    private val mutableFilmList:MutableList<String> = movieList.toMutableList()

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
            }
        }
    }
}