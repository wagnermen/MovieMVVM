package com.example.domain.movies

import com.example.data.local.UpcomingMoviesDao
import com.example.data.local.UpcomingMoviesEntity
import com.example.data.model.response.UpcomingMovieResponse
import com.example.data.remote.api.ApiMovieService
import com.example.domain.Resource
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class MovieUseCaseImplTest{

    @RelaxedMockK
    private lateinit var apiMovieService: ApiMovieService

    @RelaxedMockK
    lateinit var upcomingMoviesDao: UpcomingMoviesDao

    private lateinit var movieUseCase: MovieUseCase

    @Before
    fun onBefore(){
        MockKAnnotations.init(this)
        movieUseCase = MovieUseCaseImpl(apiMovieService, upcomingMoviesDao)
    }

    @Test
    fun testGetPopularMovies_OnlineSuccess() = runBlocking {
        val movieResponse = UpcomingMovieResponse(null, 1, null, null, null)

        coEvery { apiMovieService.upcomingMovies("1") } returns Response.success(movieResponse)
        val result = runBlocking { movieUseCase.getPopularMovies("1", true) }

        assertTrue(result is Resource.Success)
    }

    @Test
    fun testGetPopularMovies_OfflineSuccess() = runBlocking {
        val moviesFromRoom = UpcomingMoviesEntity(1, "title", "posterPath", "backdropPath", 1.0, "releaseDate", "overview")

        coEvery { upcomingMoviesDao.getMovies() } returns listOf(moviesFromRoom)
        val result = runBlocking { movieUseCase.getPopularMovies("1", false) }

        assertTrue(result is Resource.Success)
    }

    @Test
    fun testGetPopularMovies_OnlineSuccess_NoDataInRoom() = runBlocking {
        val movieResponse = UpcomingMovieResponse(null, 1, null, null, null)

        coEvery { apiMovieService.upcomingMovies("1") } returns Response.success(movieResponse)
        coEvery { upcomingMoviesDao.getMovies() } returns emptyList()

        val result = runBlocking { movieUseCase.getPopularMovies("1", true) }

        assertTrue(result is Resource.Success)
    }

    @Test
    fun testGetPopularMovies_OnlineError() = runBlocking {
        coEvery { apiMovieService.upcomingMovies("1") } returns Response.error(404, ResponseBody.create("text/plain".toMediaTypeOrNull(), ""))

        val result = runBlocking { movieUseCase.getPopularMovies("1", true) }

        assertTrue(result is Resource.Error)
    }

    @Test
    fun testGetPopularMovies_RoomError() = runBlocking {
        coEvery { upcomingMoviesDao.getMovies() } throws RuntimeException("Room error")

        val result = movieUseCase.getPopularMovies("", false)

        assertEquals(Resource.Error("Room error"), result)
    }

}