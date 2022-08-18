package eu.tutorials.composematerialdesignsamples.appmoviestmdb.domain

import eu.tutorials.composematerialdesignsamples.appmoviestmdb.data.model.MovieList
import eu.tutorials.composematerialdesignsamples.appmoviestmdb.data.remote.MovieDataSource

class MovieRepositoryImpl(private val dataSource: MovieDataSource):MovieRepository {
    override suspend fun getUpcomingMovies(): MovieList = dataSource.getUpcomingMovies()
    override suspend fun getTopRatedMovies(): MovieList = dataSource.getTopRatedMovies()
    override suspend fun getPopularMovies(): MovieList = dataSource.getPopularMovies()
}