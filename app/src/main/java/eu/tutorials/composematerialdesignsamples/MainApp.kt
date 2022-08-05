package eu.tutorials.composematerialdesignsamples

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import eu.tutorials.composematerialdesignsamples.appnews.data.RepositoryApi
import eu.tutorials.composematerialdesignsamples.appcountries.di.ApiModuleNews
import eu.tutorials.composematerialdesignsamples.appnews.domain.network.NewsService
import eu.tutorials.composematerialdesignsamples.di.torrent.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

@HiltAndroidApp
class MainApp: Application() {

    private val manager by lazy {
        NewsService(ApiModuleNews.retrofitService)
    }

    val repositoryApi by lazy {
        RepositoryApi(manager = manager)
    }

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MainApp)
            modules(listOf(networkModule, cacheModule, repoModule, viewModelModule, viewsModule))
        }
    }
}