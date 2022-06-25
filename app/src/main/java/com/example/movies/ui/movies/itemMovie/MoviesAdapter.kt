package com.example.movies.ui.movies.itemMovie

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movies.R
import com.example.movies.data.entities.Movie
import com.example.movies.databinding.MovieItemBinding
import com.example.movies.databinding.MovieItemSkeletonBinding

class MoviesAdapter(val listener: (Movie) -> Unit) :
    PagingDataAdapter<Movie, RecyclerView.ViewHolder>(MoviesDiffCallback()) {

    inner class MoviesViewHolder(private val binding: MovieItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) {
            with(binding) {
                movieTitle.text = movie.displayTitle ?: ""
                Glide
                    .with(root.context)
                    .load(movie.multimedia?.src ?: R.drawable.default_movie)
                    .into(moviePreview)
                root.setOnClickListener {
                    listener(movie)
                }
            }
        }
    }

    class MoviesDiffCallback : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItemPosition: Movie, newItemPosition: Movie) =
            oldItemPosition.displayTitle == newItemPosition.displayTitle

        override fun areContentsTheSame(oldItemPosition: Movie, newItemPosition: Movie) =
            oldItemPosition == newItemPosition
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            R.layout.movie_item -> MoviesViewHolder(
                MovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
            else -> object : RecyclerView.ViewHolder(
                MovieItemSkeletonBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                ).root){}
        }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is MoviesViewHolder -> getItem(position)?.let { holder.bind(it) }
            else -> Unit
        }
    }


    override fun getItemViewType(position: Int) =
        if (getItem(position)!=null) {
            R.layout.movie_item
        } else R.layout.movie_item_skeleton

}