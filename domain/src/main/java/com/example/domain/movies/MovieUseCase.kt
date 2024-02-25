package com.example.domain.movies

import com.example.domain.Resource
import com.example.domain.movies.model.UpcomingMovies

interface MovieUseCase {
    suspend fun getPopularMovies(page: String, isOnline: Boolean): Resource<List<UpcomingMovies>>
}