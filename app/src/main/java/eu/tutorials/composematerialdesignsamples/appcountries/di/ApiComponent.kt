package eu.tutorials.composematerialdesignsamples.appcountries.di

import dagger.Component
import eu.tutorials.composematerialdesignsamples.appcountries.domain.CountriesService
import eu.tutorials.composematerialdesignsamples.appcountries.views.CountryViewModel

@Component(modules = [ApiModuleCountry::class])
interface ApiComponent {

    fun inject(service: CountriesService)

    fun inject(viewModel: CountryViewModel)
}