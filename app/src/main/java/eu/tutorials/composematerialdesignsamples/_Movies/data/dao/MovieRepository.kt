package eu.tutorials.composematerialdesignsamples._Movies.data.dao

import eu.tutorials.composematerialdesignsamples._Movies.domain.Movie
import eu.tutorials.composematerialdesignsamples._Movies.domain.MovieGenre
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getTopRatedMoviesStream(): Flow<List<Movie>>
    fun getMoviesStream(genre: MovieGenre): Flow<List<Movie>>
    suspend fun refreshTopRated()
    suspend fun refreshGenre(genre: MovieGenre)
}