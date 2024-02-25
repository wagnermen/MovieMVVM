package com.example.moviesofttek.ui.movie.upcoming.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.domain.movies.model.UpcomingMovies
import com.example.moviesofttek.databinding.MovieUpcomingItemBinding

class UpcomingAdapter(val context: Context, private var movies: List<UpcomingMovies>, private val listener:
    (UpcomingMovies) -> Unit) : RecyclerView.Adapter<UpcomingAdapter.UpcomingHolder>() {

    inner class UpcomingHolder(
        private val mViewBinding: MovieUpcomingItemBinding,
    ) : RecyclerView.ViewHolder(mViewBinding.root) {
        val imgMovie: ImageView = mViewBinding.upcomingImageView
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UpcomingHolder = UpcomingHolder(MovieUpcomingItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(
        holder: UpcomingHolder,
        position: Int
    ) {
        val item = movies[position]
        holder.imgMovie.load("https://image.tmdb.org/t/p/w500/${item.posterPath}")

        holder.itemView.setOnClickListener { listener(item) }
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    fun updateList(movies: List<UpcomingMovies>, isEmptyList: (Boolean) -> Unit) {
        isEmptyList(movies.isEmpty())
        this.movies = movies
        notifyDataSetChanged()
    }
}