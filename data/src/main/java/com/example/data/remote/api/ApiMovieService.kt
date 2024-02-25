package com.example.data.remote.api

import com.example.data.model.response.UpcomingMovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiMovieService {
    @GET("3/movie/popular")
    suspend fun upcomingMovies(@Query("page") page: String): Response<UpcomingMovieResponse>
}