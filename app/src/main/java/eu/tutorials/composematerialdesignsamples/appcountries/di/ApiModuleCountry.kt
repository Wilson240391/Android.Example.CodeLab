package eu.tutorials.composematerialdesignsamples.appcountries.di

import dagger.Module
import dagger.Provides
import eu.tutorials.composematerialdesignsamples.appcountries.data.countries.CountriesApi
import eu.tutorials.composematerialdesignsamples.appcountries.domain.CountriesService
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
class ApiModuleCountry {

    private val BASE_URL = "https://raw.githubusercontent.com"

    @Provides
    fun provideCountriesApi(): CountriesApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(CountriesApi::class.java)
    }

    @Provides
    fun provideCountriesService(): CountriesService {
        return CountriesService()
    }
}