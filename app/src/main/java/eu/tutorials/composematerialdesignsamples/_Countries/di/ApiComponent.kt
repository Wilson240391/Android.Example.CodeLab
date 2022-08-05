package eu.tutorials.composematerialdesignsamples._Countries.di

import dagger.Component
import eu.tutorials.composematerialdesignsamples._Countries.domain.CountriesService
import eu.tutorials.composematerialdesignsamples._Countries.views.CountryViewModel

@Component(modules = [ApiModuleCountry::class])
interface ApiComponent {

    fun inject(service: CountriesService)

    fun inject(viewModel: CountryViewModel)
}