package eu.tutorials.composematerialdesignsamples.appmovies.data.dao

import eu.tutorials.composematerialdesignsamples.appmovies.domain.Movie
import eu.tutorials.composematerialdesignsamples.appmovies.domain.MovieGenre
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getTopRatedMoviesStream(): Flow<List<Movie>>
    fun getMoviesStream(genre: MovieGenre): Flow<List<Movie>>
    suspend fun refreshTopRated()
    suspend fun refreshGenre(genre: MovieGenre)
}