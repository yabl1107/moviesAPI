package com.myself.moviesappapi.state

import com.myself.moviesappapi.model.singleGameModel

data class MovieState (
    val isLoading: Boolean = false,
    val movie: singleGameModel? = null,
    val error: String? = null
)