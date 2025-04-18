package com.myself.moviesappapi.data

import com.myself.moviesappapi.model.GamesModel
import com.myself.moviesappapi.model.singleGameModel
import com.myself.moviesappapi.util.Constants.Companion.GET_MOVIES
import com.myself.moviesappapi.util.Constants.Companion.GET_MOVIE_DETAIL
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieApi {
    @GET(GET_MOVIES)
    suspend fun getMovies(): Response<GamesModel>

    @GET(GET_MOVIE_DETAIL)
    suspend fun getMovieById(@Path("id") id: Int): Response<singleGameModel>

}