package eu.tutorials.composematerialdesignsamples.appmoviestmdb.domain

import eu.tutorials.composematerialdesignsamples.appmoviestmdb.data.model.MovieList

interface MovieRepository {

    suspend fun getUpcomingMovies(): MovieList
    suspend fun getTopRatedMovies(): MovieList
    suspend fun getPopularMovies(): MovieList
}