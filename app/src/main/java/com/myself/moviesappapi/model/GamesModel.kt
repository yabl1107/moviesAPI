package com.myself.moviesappapi.model

data class MoviesModel(
    val page : Int,
    val total_pages: Int,
    val total_results: Int,
    val results: List<MovieList>
)

data class MovieList(
    val id: Int,
    val original_title: String,
    val poster_path: String,
    val release_date: String,
)
