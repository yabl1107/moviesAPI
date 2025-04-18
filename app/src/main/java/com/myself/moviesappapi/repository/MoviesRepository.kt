package com.myself.moviesappapi.repository

import android.util.Log
import com.myself.moviesappapi.data.MovieApi
import com.myself.moviesappapi.model.MovieList
import com.myself.moviesappapi.model.singleGameModel
import javax.inject.Inject

class MoviesRepository @Inject constructor(
    private val movieApi: MovieApi
){
    suspend fun getMovies(): List<MovieList>?{
        val response = movieApi.getMovies()
        if(response.isSuccessful){
            return response.body()?.results
        }
        return null
    }
    suspend fun getMovieDetail(id : Int): singleGameModel? {
        val response = movieApi.getMovieById(id)
        if(response.isSuccessful){
            return response.body()!!
        }
        return null
    }

}