package eu.tutorials.composematerialdesignsamples

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import eu.tutorials.composematerialdesignsamples._News.data.RepositoryApi
import eu.tutorials.composematerialdesignsamples._Countries.di.ApiModuleNews
import eu.tutorials.composematerialdesignsamples._News.domain.network.NewsService

@HiltAndroidApp
class MainApp: Application() {

    private val manager by lazy {
        NewsService(ApiModuleNews.retrofitService)
    }

    val repositoryApi by lazy {
        RepositoryApi(manager = manager)
    }
}