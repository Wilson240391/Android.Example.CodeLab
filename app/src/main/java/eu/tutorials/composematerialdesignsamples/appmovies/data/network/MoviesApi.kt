package eu.tutorials.composematerialdesignsamples.appmovies.data.network

import eu.tutorials.composematerialdesignsamples.appmovies.domain.Movie
import eu.tutorials.composematerialdesignsamples.appmovies.domain.WrapperMovieResults
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