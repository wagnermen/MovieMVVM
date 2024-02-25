package com.example.domain.movies.model

import java.io.Serializable

data class UpcomingMovies(
        val id: Int,
        val title: String? = null,
        val posterPath: String? = null,
        val backdropPath: String? = null,
        val voteAverage: Double? = null,
        val releaseDate: String? = null,
        val overview: String? = null,
    ): Serializable