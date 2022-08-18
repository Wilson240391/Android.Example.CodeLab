package eu.tutorials.composematerialdesignsamples.di.torrent

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import eu.tutorials.composematerialdesignsamples.apptorrentmovies.domain.network.ApiService
import eu.tutorials.composematerialdesignsamples.util.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


val networkModule = module {

    single{
       HttpLoggingInterceptor()
           .setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    single{
        OkHttpClient.Builder()
            .addInterceptor(get<HttpLoggingInterceptor>())
            .callTimeout(20, TimeUnit.SECONDS)
            .build()
    }
    single {
        GsonConverterFactory.create()
    }
    single {
        CoroutineCallAdapterFactory()
    }

    single {
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(get<GsonConverterFactory>())
            .addCallAdapterFactory(get<CoroutineCallAdapterFactory>())
            .client(get<OkHttpClient>())
            .build()
    }

    factory {
        get<Retrofit>().create(ApiService::class.java)
    }
}