package eu.tutorials.composematerialdesignsamples.domain.network

import eu.tutorials.composematerialdesignsamples.data.repository.CountriesApi
import eu.tutorials.composematerialdesignsamples.di.DaggerApiComponent
import eu.tutorials.composematerialdesignsamples.domain.models.Country
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