package eu.tutorials.composematerialdesignsamples.data.room

import eu.tutorials.composematerialdesignsamples.domain.models.movies.Movie
import eu.tutorials.composematerialdesignsamples.domain.models.movies.WrapperMovieResults
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