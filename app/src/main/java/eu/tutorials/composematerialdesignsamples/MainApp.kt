package eu.tutorials.composematerialdesignsamples

import android.app.Application
import eu.tutorials.composematerialdesignsamples.data.repository.RepositoryApi
import eu.tutorials.composematerialdesignsamples.domain.network.Api
import eu.tutorials.composematerialdesignsamples.domain.network.NewsManager

class MainApp: Application() {

    private val manager by lazy {
        NewsManager(Api.retrofitService)
    }

    val repositoryApi by lazy {
        RepositoryApi(manager = manager)
    }
}