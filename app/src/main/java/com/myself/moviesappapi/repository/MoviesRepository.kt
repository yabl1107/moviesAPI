package com.myself.moviesappapi.repository

import android.util.Log
import com.myself.moviesappapi.data.MovieApi
import com.myself.moviesappapi.model.MovieList
import com.myself.moviesappapi.model.singleGameModel
import kotlinx.coroutines.delay
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

    suspend fun getMovieByTitle(title : String) : List<MovieList>? {
        val response = movieApi.getMovieByTitle(title)
        if(response.isSuccessful){
            return response.body()?.results
        }
        return null
    }

    suspend fun getMoviePage(page : Int) : List<MovieList>? {
        delay(3000)
        val response = movieApi.getMoviePage(page)
        if(response.isSuccessful){
            return response.body()?.results
        }
        return null
    }

}