package eu.tutorials.composematerialdesignsamples.appmoviestmdb.data.remote

import eu.tutorials.composematerialdesignsamples.appmoviestmdb.data.model.MovieList
import eu.tutorials.composematerialdesignsamples.appmoviestmdb.domain.ApiService
import eu.tutorials.composematerialdesignsamples.util.tmdb.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MovieDataSource(private val webService: ApiService) {

    suspend fun getUpcomingMovies(): MovieList = withContext(Dispatchers.IO){webService.getUpcomingMovies(Constants.API_KEY)}

    suspend fun getTopRatedMovies(): MovieList = withContext(Dispatchers.IO){webService.getTopRatedMovies(Constants.API_KEY)}

    suspend fun getPopularMovies(): MovieList = withContext(Dispatchers.IO){webService.getPopulardMovies(Constants.API_KEY)}
}