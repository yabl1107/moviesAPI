package com.myself.moviesappapi.data


import com.myself.moviesappapi.model.MoviesModel
import com.myself.moviesappapi.model.singleGameModel
import com.myself.moviesappapi.util.Constants.Companion.GET_BY_TITLE
import com.myself.moviesappapi.util.Constants.Companion.GET_MOVIES
import com.myself.moviesappapi.util.Constants.Companion.GET_MOVIE_DETAIL
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {
    @GET(GET_MOVIES)
    suspend fun getMovies(): Response<MoviesModel>

    @GET(GET_MOVIE_DETAIL)
    suspend fun getMovieById(@Path("id") id: Int): Response<singleGameModel>

    @GET(GET_BY_TITLE)
    suspend fun getMovieByTitle(@Query("query") title: String): Response<MoviesModel>

    @GET(GET_MOVIES)
    suspend fun getMoviePage(@Query("page") page: Int): Response<MoviesModel>

}