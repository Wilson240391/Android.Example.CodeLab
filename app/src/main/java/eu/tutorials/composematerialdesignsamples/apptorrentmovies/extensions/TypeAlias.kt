package com.kpstv.yts.extensions

import eu.tutorials.composematerialdesignsamples.apptorrentmovies.data.model.Movie


typealias ExceptionCallback = (Exception) -> Unit
typealias SimpleCallback = () -> Unit

data class CastMoviesCallback(
    val onFailure: ExceptionCallback?,
    val onComplete: (results: List<Movie>) -> Unit
)

data class SuggestionCallback(
    val onStarted: SimpleCallback? = null,
    val onComplete: (movies: ArrayList<Movie>, tag: String?, isMoreAvailable: Boolean) -> Unit,
    val onFailure: ExceptionCallback? = null
)