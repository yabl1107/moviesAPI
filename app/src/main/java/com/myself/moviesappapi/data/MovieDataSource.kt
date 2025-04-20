package com.myself.moviesappapi.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.myself.moviesappapi.model.MovieList
import com.myself.moviesappapi.repository.MoviesRepository

class MovieDataSource (
    private val repository: MoviesRepository
): PagingSource<Int, MovieList>(){
    override fun getRefreshKey(state: PagingState<Int, MovieList>): Int? {
        return state.anchorPosition?.let{ anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieList> {
        return try {
            val nextPageNumber = params.key ?: 1
            val response = repository.getMoviePage(nextPageNumber)
            LoadResult.Page(
                data = response ?: emptyList(),
                prevKey = null,
                nextKey = if(response?.isNotEmpty() == true) nextPageNumber + 1 else null
            )
        }catch(e: Exception){
            LoadResult.Error(e)
        }
    }

}