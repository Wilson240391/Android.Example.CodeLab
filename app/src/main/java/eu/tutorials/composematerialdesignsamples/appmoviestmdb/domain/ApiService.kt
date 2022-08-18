package eu.tutorials.composematerialdesignsamples.appmoviestmdb.domain

import com.google.gson.GsonBuilder
import eu.tutorials.composematerialdesignsamples.appmoviestmdb.data.model.MovieList
import eu.tutorials.composematerialdesignsamples.apptorrentmovies.data.model.MoviesResponse
import eu.tutorials.composematerialdesignsamples.util.tmdb.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(@Query("api_key") apiKey: String): MovieList

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(@Query("api_key") apiKey: String): MovieList

    @GET("movie/popular")
    suspend fun getPopulardMovies(@Query("api_key") apiKey: String): MovieList
}

object RetrofitClient {

    val webservice by lazy {
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build().create(ApiService::class.java)
    }

}
