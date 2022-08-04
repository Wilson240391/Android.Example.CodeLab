package eu.tutorials.composematerialdesignsamples.data.repository.movies

import eu.tutorials.composematerialdesignsamples.domain.models.movies.Movie
import eu.tutorials.composematerialdesignsamples.domain.models.movies.MovieGenre
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getTopRatedMoviesStream(): Flow<List<Movie>>
    fun getMoviesStream(genre: MovieGenre): Flow<List<Movie>>
    suspend fun refreshTopRated()
    suspend fun refreshGenre(genre: MovieGenre)
}