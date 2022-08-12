package eu.tutorials.composematerialdesignsamples.apptorrentmovies.data.source.network

import eu.tutorials.composematerialdesignsamples.apptorrentmovies.data.model.MovieDetails
import eu.tutorials.composematerialdesignsamples.apptorrentmovies.data.model.MoviesResponse
import eu.tutorials.composematerialdesignsamples.apptorrentmovies.data.model.MoviesSuggestResponse

class NetworkSourceImpl(private val apiService: ApiService):
    INetworkSource {


    override suspend fun searchMovie(search: String, page: Int): MoviesResponse = apiService.searchInMovie(search, page)

    override suspend fun getMoviesCategory(category: String, page: Int): MoviesResponse
            = apiService.getMoviesCategoryList(category, page)

    override suspend fun movieDetails(id: Int): MovieDetails =
        apiService.getMovieDetails(id)

    override suspend fun rankMovies(page: Int): MoviesResponse =
        apiService.getRankMovies(page)

    override suspend fun Suggestions(id: Int): MoviesSuggestResponse =
        apiService.getSuggestions(id)
}