package com.myself.moviesappapi.state

import com.myself.moviesappapi.model.singleGameModel

data class MovieState (
    val isLoading: Boolean = false,
    val movie: singleGameModel = singleGameModel(),
    val error: String? = null
)