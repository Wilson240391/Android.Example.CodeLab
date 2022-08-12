package eu.tutorials.composematerialdesignsamples.apptorrentmovies.data.source.network

import eu.tutorials.composematerialdesignsamples.apptorrentmovies.data.model.MovieDetails
import eu.tutorials.composematerialdesignsamples.apptorrentmovies.data.model.MoviesResponse
import eu.tutorials.composematerialdesignsamples.apptorrentmovies.data.model.MoviesSuggestResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("list_movies.json")
    suspend fun searchInMovie(@Query("query_term") search: String, @Query("page") page: Int): MoviesResponse

    @GET("list_movies.json")
    suspend fun getMoviesCategoryList(@Query("genre") category: String, @Query("page") page: Int) : MoviesResponse

    @GET("movie_details.json?with_images=true&with_cast=true")
    suspend fun getMovieDetails(@Query("movie_id") id: Int) : MovieDetails

    @GET("list_movies.json?sort_by=rating")
    suspend fun getRankMovies(@Query("page") page: Int): MoviesResponse

    @GET("movie_suggestions.json")
    suspend fun getSuggestions(@Query("movie_id") id: Int): MoviesSuggestResponse
}