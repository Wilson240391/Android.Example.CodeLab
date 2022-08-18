package eu.tutorials.composematerialdesignsamples.appmoviestmdb.data.remote

import eu.tutorials.composematerialdesignsamples.appmoviestmdb.data.model.MovieList
import eu.tutorials.composematerialdesignsamples.appmoviestmdb.domain.ApiService
import eu.tutorials.composematerialdesignsamples.util.tmdb.Constants

class MovieDataSource(private val webService: ApiService) {

    suspend fun getUpcomingMovies(): MovieList = webService.getUpcomingMovies(Constants.API_KEY)

    suspend fun getTopRatedMovies(): MovieList = webService.getTopRatedMovies(Constants.API_KEY)

    suspend fun getPopularMovies(): MovieList = webService.getPopulardMovies(Constants.API_KEY)
}