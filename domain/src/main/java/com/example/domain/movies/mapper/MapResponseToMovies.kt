package com.example.domain.movies.mapper

import com.example.data.local.UpcomingMoviesEntity
import com.example.data.model.response.UpcomingMovieResponse
import com.example.domain.movies.model.UpcomingMovies

class MapResponseToMovies {

    companion object {
        fun mapResponseToMovies(response: UpcomingMovieResponse): List<UpcomingMovies> {
            val movies = mutableListOf<UpcomingMovies>()
            response.results?.forEach { movieResponse ->
                val movie = UpcomingMovies(
                    id = movieResponse.id,
                    title = movieResponse.title,
                    posterPath = movieResponse.poster_path,
                    backdropPath = movieResponse.backdrop_path,
                    voteAverage = movieResponse.vote_average,
                    releaseDate = movieResponse.release_date,
                    overview = movieResponse.overview
                )
                movies.add(movie)
            }
            return movies
        }

        fun mapEntityToMovies(entity: List<UpcomingMoviesEntity>): List<UpcomingMovies> {
            val movies = mutableListOf<UpcomingMovies>()
            entity.forEach { moviesEntity ->
                val movie = UpcomingMovies(
                    id = moviesEntity.id,
                    title = moviesEntity.title,
                    posterPath = moviesEntity.posterPath,
                    backdropPath = moviesEntity.backdropPath,
                    voteAverage = moviesEntity.voteAverage,
                    releaseDate = moviesEntity.releaseDate,
                    overview = moviesEntity.overview
                )
                movies.add(movie)
            }
            return movies
        }
    }

}