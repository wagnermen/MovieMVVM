package com.example.moviesofttek.ui.movie.upcoming

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.domain.Resource
import com.example.domain.movies.MovieUseCaseImpl
import com.example.domain.movies.model.UpcomingMovies
import com.example.moviesofttek.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpcomingViewModel @Inject constructor(private val movieUseCaseImpl: MovieUseCaseImpl): BaseViewModel() {

    private val _upcomingMovie = MutableLiveData<List<UpcomingMovies>?>()
    val upcomingMovie: LiveData<List<UpcomingMovies>?> = _upcomingMovie

    val error = MutableLiveData<String?>()

    fun getUpcomingMovies(page: String,isOnline: Boolean) {
        viewModelScope.launch {
            isViewLoading.value = true
            when (val result = movieUseCaseImpl.getPopularMovies(page = page, isOnline = isOnline)) {
                is Resource.Success -> {
                    _upcomingMovie.value = result.data
                    isViewLoading.value = false
                }
                is Resource.Error -> {
                    error.value = result.message
                    isViewLoading.value = false
                }
            }
        }
    }

}