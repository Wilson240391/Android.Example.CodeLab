package eu.tutorials.composematerialdesignsamples._Movies.data.api

import eu.tutorials.composematerialdesignsamples._Movies.domain.Movie
import eu.tutorials.composematerialdesignsamples._Movies.domain.WrapperMovieResults
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET("movie/top_rated")
    @WrapperMovieResults
    suspend fun getTopRated(): List<Movie>

    @GET("discover/movie")
    @WrapperMovieResults
    suspend fun getMoviesForGenre(@Query("with_genres") ids: String): List<Movie>
}