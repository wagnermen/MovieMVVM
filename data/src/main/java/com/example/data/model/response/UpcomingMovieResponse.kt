package com.example.data.model.response

data class UpcomingMovieResponse(
    val dates: Dates? = null,
    val page: Int,
    val results: List<Result>? = emptyList(),
    val total_pages: Int? = null,
    val total_results: Int? = null
)