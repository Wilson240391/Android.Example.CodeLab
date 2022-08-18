package eu.tutorials.composematerialdesignsamples.apptorrentmovies.domain.network

import eu.tutorials.composematerialdesignsamples.apptorrentmovies.data.model.MovieDetails
import eu.tutorials.composematerialdesignsamples.apptorrentmovies.data.model.MoviesResponse
import eu.tutorials.composematerialdesignsamples.apptorrentmovies.data.model.MoviesSuggestResponse

interface INetworkSource {

    suspend fun searchMovie(search: String,page: Int): MoviesResponse
    suspend fun getMoviesCategory(category: String, page: Int): MoviesResponse
    suspend fun movieDetails(id: Int): MovieDetails
    suspend fun rankMovies(page: Int): MoviesResponse
    suspend fun Suggestions(id: Int): MoviesSuggestResponse

}