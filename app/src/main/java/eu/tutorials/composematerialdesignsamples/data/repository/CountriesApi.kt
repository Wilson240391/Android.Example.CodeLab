package eu.tutorials.composematerialdesignsamples.data.repository

import eu.tutorials.composematerialdesignsamples.domain.models.Country
import io.reactivex.Single
import retrofit2.http.GET

interface CountriesApi {

    @GET("DevTides/countries/master/countriesV2.json")
    fun getCountries(): Single<List<Country>>
}