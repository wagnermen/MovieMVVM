package com.example.moviesofttek.ui.movie.upcoming

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.domain.movies.model.UpcomingMovies
import com.example.moviesofttek.R
import com.example.moviesofttek.databinding.FragmentUpcomingMovieBinding
import com.example.moviesofttek.ui.BaseFragment
import com.example.moviesofttek.ui.movie.upcoming.adapter.UpcomingAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UpcomingMovieFragment : BaseFragment() {
    private lateinit var binding: FragmentUpcomingMovieBinding
    private val upcomingViewModel: UpcomingViewModel by viewModels()
    private lateinit var upcomingAdapter: UpcomingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        upcomingViewModel.getUpcomingMovies("1", isInternetAvailable)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUpcomingMovieBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observer()
    }

    private fun observer(){
        upcomingViewModel.isViewLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) dialogProgress?.show() else dialogProgress?.dismiss()
        }
        upcomingViewModel.upcomingMovie.observe(viewLifecycleOwner) {
            if (it?.isNotEmpty() == true) {
                Log.d("listaInicio:", "$it")
                setAdapter(it)
            }else {
                Toast.makeText(
                    requireContext(),
                    "No hay peliculas para mostrar",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        upcomingViewModel.error.observe(viewLifecycleOwner) { error ->
            if (error != null) {
                Toast.makeText(requireContext(), "Ocurrió un error inesperado", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setAdapter(lstItem: List<UpcomingMovies>){
        upcomingAdapter = UpcomingAdapter(requireContext() ,lstItem){ movie ->
            val bundle = Bundle().apply {
                putSerializable("detailMovie", movie)
            }
            findNavController().navigate(R.id.action_upcomingMovieFragment_to_detailMovieFragment, bundle)
        }
        //binding.movieUpcomingRecycler.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.movieUpcomingRecycler.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.movieUpcomingRecycler.adapter = upcomingAdapter
    }

}