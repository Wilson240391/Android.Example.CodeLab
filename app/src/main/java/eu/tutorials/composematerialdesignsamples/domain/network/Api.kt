package eu.tutorials.composematerialdesignsamples.domain.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import eu.tutorials.composematerialdesignsamples.data.repository.NewsService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object Api {

    private val BASE_URL = "https://newsapi.org/v2/"
    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    private val logging = HttpLoggingInterceptor()
    const val Api_Key = "717d3e541fa1492e8aa2e7f163972bf5"

    private val httpClient = OkHttpClient.Builder().apply {
        addInterceptor(
            Interceptor{chain->
                val builder = chain.request().newBuilder()
                builder.header("X-Api-Key", Api_Key)
                return@Interceptor chain.proceed(builder.build())
            }
        )
        logging.level = HttpLoggingInterceptor.Level.BODY
        addNetworkInterceptor(logging)
    }.build()

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(BASE_URL)
        .client(httpClient)
        .build()

    val retrofitService: NewsService by lazy { retrofit.create(NewsService::class.java) }
}