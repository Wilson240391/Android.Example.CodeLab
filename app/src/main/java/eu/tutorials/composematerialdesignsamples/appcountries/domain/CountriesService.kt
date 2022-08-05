package eu.tutorials.composematerialdesignsamples.appcountries.domain

import eu.tutorials.composematerialdesignsamples.appcountries.data.countries.CountriesApi
import eu.tutorials.composematerialdesignsamples.appcountries.di.DaggerApiComponent
import io.reactivex.Single
import javax.inject.Inject

class CountriesService {

    @Inject
    lateinit var api: CountriesApi

    init {
        DaggerApiComponent.create().inject(this)
    }

    fun getCountries(): Single<List<Country>> {
        return api.getCountries()
    }
}