package eu.tutorials.composematerialdesignsamples.di

import dagger.Component
import eu.tutorials.composematerialdesignsamples.domain.network.CountriesService
import eu.tutorials.composematerialdesignsamples.views.activitys.countries.CountryViewModel

@Component(modules = [ApiModule::class])
interface ApiComponent {

    fun inject(service: CountriesService)

    fun inject(viewModel: CountryViewModel)
}