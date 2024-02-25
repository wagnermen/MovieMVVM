package com.example.moviesofttek.ui.movie.detaill

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import coil.load
import com.example.domain.movies.model.UpcomingMovies
import com.example.moviesofttek.R
import com.example.moviesofttek.databinding.FragmentDetailMovieBinding
import com.example.moviesofttek.databinding.FragmentUpcomingMovieBinding

class DetailMovieFragment : Fragment() {
   private lateinit var binding: FragmentDetailMovieBinding
   private lateinit var upcomingMovies: UpcomingMovies

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        upcomingMovies = arguments?.getSerializable("detailMovie") as UpcomingMovies
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.txtTitle.text = upcomingMovies.title
        binding.txtNotaDate.text = "Nota: " + upcomingMovies.voteAverage.toString() + "\nFecha lanzamiento: " + upcomingMovies.releaseDate
        binding.textViewDescription.text = upcomingMovies.overview
        binding.imageViewPoster.load("https://image.tmdb.org/t/p/w500/${upcomingMovies.posterPath}")
        binding.imageViewBackground.load("https://image.tmdb.org/t/p/w500/${upcomingMovies.backdropPath}")

        val fadeInAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.image_anim)
        binding.imageViewBackground.startAnimation(fadeInAnimation)
    }

}