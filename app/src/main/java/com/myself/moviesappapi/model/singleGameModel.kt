package com.myself.moviesappapi.model

data class singleGameModel(
    val title : String = "",
    val overview : String = "",
    val release_date : String = "",
    val runtime : Int = 0,
    val genres : List<genreModel>,
    val backdrop_path : String = "",
    val homepage : String = "",
    val origin_country : String = "",
    val budget : Long = 0L,
    val revenue : Long = 0L,
)

data class genreModel(
    val name : String = "",
    val id : Int = 0
)
