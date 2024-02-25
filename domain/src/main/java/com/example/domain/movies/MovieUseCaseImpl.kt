package com.example.domain.movies

import android.util.Log
import com.example.data.local.UpcomingMoviesDao
import com.example.data.local.UpcomingMoviesEntity.Companion.toMoviesDataEntity
import com.example.data.remote.api.ApiMovieService
import com.example.domain.Resource
import com.example.domain.movies.mapper.MapResponseToMovies.Companion.mapEntityToMovies
import com.example.domain.movies.mapper.MapResponseToMovies.Companion.mapResponseToMovies
import com.example.domain.movies.model.UpcomingMovies
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MovieUseCaseImpl @Inject constructor(
    private val apiMovieService: ApiMovieService,
    private val upcomingMoviesDao: UpcomingMoviesDao)
    : MovieUseCase {

    override suspend fun getPopularMovies(page: String, isOnline: Boolean): Resource<List<UpcomingMovies>> {
        return withContext(Dispatchers.IO) {
            try {
                if (!isOnline) {
                    val moviesFromRoom = upcomingMoviesDao.getMovies()
                    if (moviesFromRoom.isNotEmpty()) {
                        Log.d("clientsUSeCase3", "room: ")
                        return@withContext Resource.Success(mapEntityToMovies(moviesFromRoom))
                    }
                }
                val result = apiMovieService.upcomingMovies(page)
                if (result.isSuccessful) {
                    val movieResponse = result.body()
                    val movies = movieResponse?.let { mapResponseToMovies(it) }
                    movieResponse?.results?.let {results ->
                        upcomingMoviesDao.insertMovies(results.map { it.toMoviesDataEntity() })
                    }
                    Resource.Success(movies)
                } else {
                    Resource.Error(result.toString())
                }
            } catch (e: Exception) {
                Log.d("clientsUSeCase3", "insertClient: ${e.message}")
                Resource.Error(e.message)
            }
        }
    }

}