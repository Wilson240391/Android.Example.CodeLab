package eu.tutorials.composematerialdesignsamples.appcountries.data.countries

import eu.tutorials.composematerialdesignsamples.appcountries.domain.Country
import io.reactivex.Single
import retrofit2.http.GET

interface CountriesApi {

    @GET("DevTides/countries/master/countriesV2.json")
    fun getCountries(): Single<List<Country>>
}