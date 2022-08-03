package eu.tutorials.composematerialdesignsamples

import android.app.Application
import eu.tutorials.composematerialdesignsamples.data.repository.RepositoryApi
import eu.tutorials.composematerialdesignsamples.di.ApiModuleNews
import eu.tutorials.composematerialdesignsamples.domain.network.NewsService

class MainApp: Application() {

    private val manager by lazy {
        NewsService(ApiModuleNews.retrofitService)
    }

    val repositoryApi by lazy {
        RepositoryApi(manager = manager)
    }
}