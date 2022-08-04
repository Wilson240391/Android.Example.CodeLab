package eu.tutorials.composematerialdesignsamples

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import eu.tutorials.composematerialdesignsamples.data.repository.news.RepositoryApi
import eu.tutorials.composematerialdesignsamples.di.countries.ApiModuleNews
import eu.tutorials.composematerialdesignsamples.domain.network.NewsService

@HiltAndroidApp
class MainApp: Application() {

    private val manager by lazy {
        NewsService(ApiModuleNews.retrofitService)
    }

    val repositoryApi by lazy {
        RepositoryApi(manager = manager)
    }
}