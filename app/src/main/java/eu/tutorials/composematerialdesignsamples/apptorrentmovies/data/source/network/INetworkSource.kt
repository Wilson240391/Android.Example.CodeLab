package eu.tutorials.composematerialdesignsamples.apptorrentmovies.data.source.network

import eu.tutorials.composematerialdesignsamples.apptorrentmovies.data.model.MovieDetails
import eu.tutorials.composematerialdesignsamples.apptorrentmovies.data.model.MoviesResponse

interface INetworkSource {

    suspend fun searchMovie(search: String,page: Int): MoviesResponse
    suspend fun getMoviesCategory(category: String, page: Int): MoviesResponse
    suspend fun movieDetails(id: Int): MovieDetails
    suspend fun rankMovies(page: Int): MoviesResponse

}