package eu.tutorials.composematerialdesignsamples.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import eu.tutorials.composematerialdesignsamples.appmovies.data.network.Api
import eu.tutorials.composematerialdesignsamples.appmovies.domain.MoviesJsonConverter
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

private const val TMDB_BASE_URL = "https://api.themoviedb.org/3/"

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun provideApi(): Api {

        val authQueryAppenderInterceptor = Interceptor { chain ->
            val requestBuilder = chain.request().newBuilder()

            val url = chain.request().url
            val urlBuilder = url.newBuilder()
            if (url.queryParameter("api_key") == null) {
                urlBuilder.addQueryParameter("api_key", "b99f04fa2f79a9e6e4665dbdc17d8f23")
            }
            chain.proceed(
                requestBuilder
                    .url(urlBuilder.build())
                    .build()
            )
        }

        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val okHttpBuilder = OkHttpClient.Builder()
            .addInterceptor(authQueryAppenderInterceptor)
            .addInterceptor(loggingInterceptor)

        val moshi = Moshi.Builder()
            .add(MoviesJsonConverter())
            .add(KotlinJsonAdapterFactory())
            .build()

        return Retrofit.Builder()
            .baseUrl(TMDB_BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(okHttpBuilder.build())
            .build()
            .create(Api::class.java)
    }
}