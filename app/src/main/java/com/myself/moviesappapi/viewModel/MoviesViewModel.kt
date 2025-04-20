package com.myself.moviesappapi.viewModel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.myself.moviesappapi.data.MovieDataSource
import com.myself.moviesappapi.model.MovieList
import com.myself.moviesappapi.model.singleGameModel
import com.myself.moviesappapi.repository.MoviesRepository
import com.myself.moviesappapi.state.MovieState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.cache
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val repository: MoviesRepository
) : ViewModel(){

        private val _movies = MutableStateFlow<List<MovieList>>(emptyList())
        val movies = _movies.asStateFlow()

        private val _moviesFiltered = MutableStateFlow<List<MovieList>>(emptyList())
        val moviesFiltered = _moviesFiltered.asStateFlow()

        var movieState by mutableStateOf(MovieState())
            private set

        init {
            fetchMovies()
        }

        val moviePage = Pager(PagingConfig(pageSize = 3)){
            MovieDataSource(repository)
        }.flow.cachedIn(viewModelScope)

        private fun fetchMovies(){
            Log.d("Log from ViewModel", "Fetch Calleddddddddddd")
            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    val result = repository.getMovies()
                    _movies.value = result ?: emptyList()
                }
            }
        }

    fun fetchMovieByTitle(title : String){
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.getMovieByTitle(title)
            _moviesFiltered.value = result ?: emptyList()
        }
    }


    fun fetchMovieDetail(id : Int){
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.getMovieDetail(id)
            //Log.d("Log from ViewModel", "Movie Detail fetched: ${result}")
            result?.let{
                movieState = movieState.copy(
                    isLoading = false,
                    movie = it,
                    error = null
                )
            }
        }
    }

    fun clean() {
        movieState = movieState.copy(
            isLoading = false,
            movie = singleGameModel(),
            error = null
        )
    }

    fun cleanFiltered() {
        _moviesFiltered.value = emptyList()
    }


}