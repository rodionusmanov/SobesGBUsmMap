package com.example.sobesgbusmmap.filmApp.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.Scale
import com.example.sobesgbusmmap.databinding.FilmItemBinding
import com.example.sobesgbusmmap.databinding.FilmItemLoadingBinding
import com.example.sobesgbusmmap.filmApp.model.Movie
import com.example.sobesgbusmmap.filmApp.utils.convertRatingToColor

class FilmListAdapter(
//    private val movieList: List<Movie>,
    private var fragment: FilmListFragment,
    val callbackOpenFilmInfo: IOpenFilmInfo
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(), PaginationAdapterCallback {

    private val item: Int = 0
    private val loading: Int = 1

    private var isLoadingAdded: Boolean = false
    private var retryPageLoad: Boolean = false

    private var errorMsg: String? = ""

    private var moviesModels: MutableList<Movie> = ArrayList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        var viewHolder: RecyclerView.ViewHolder
        when (viewType) {
            item -> {
                val binding =
                    FilmItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                viewHolder = FilmViewHolder(binding.root)
            }

            loading -> {
                val binding =
                    FilmItemLoadingBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )

                viewHolder = FilmItemLoadingViewHolder(binding.root)
            }

            else -> {
                val binding =
                    FilmItemLoadingBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )

                viewHolder = FilmItemLoadingViewHolder(binding.root)
            }
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            item -> {
                val filmViewHolder: FilmViewHolder = holder as FilmViewHolder
                holder.bind(moviesModels[position])
            }

            loading -> {
                val filmItemLoadingViewHolder: FilmItemLoadingViewHolder =
                    holder as FilmItemLoadingViewHolder
                holder.bind()
            }
        }
    }

    fun addAll(movies: MutableList<Movie>) {
        for (movie in movies) {
            add(movie)
        }
    }

    fun add(movie: Movie) {
        moviesModels.add(movie)
        notifyItemInserted(moviesModels.size - 1)
    }

    fun addLoadingFooter() {
        isLoadingAdded = true
//        add(MutableList<Movie>())
    }

    fun removeLoadingFooter() {
        isLoadingAdded = false

        val position: Int = moviesModels.size - 1
        val movie: Movie = moviesModels[position]

        if (movie != null) {
            moviesModels.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    override fun getItemCount() = moviesModels.size

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) {
            item
        } else {
            if (position == moviesModels.size - 1 && isLoadingAdded) {
                loading
            } else {
                item
            }
        }
    }

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

    inner class FilmItemLoadingViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind() {
            FilmItemLoadingBinding.bind(itemView).apply {
                progressbar.visibility = View.VISIBLE
            }
        }
    }

    override fun retryPageLoad() {
        fragment.loadNextPage()
    }
}