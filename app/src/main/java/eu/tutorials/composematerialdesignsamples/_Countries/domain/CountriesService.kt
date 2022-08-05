package eu.tutorials.composematerialdesignsamples._Countries.domain

import eu.tutorials.composematerialdesignsamples._Countries.data.countries.CountriesApi
import eu.tutorials.composematerialdesignsamples._Countries.di.DaggerApiComponent
import eu.tutorials.composematerialdesignsamples._Countries.domain.Country
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