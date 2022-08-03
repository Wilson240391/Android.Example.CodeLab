package eu.tutorials.composematerialdesignsamples.domain.network

import eu.tutorials.composematerialdesignsamples.data.repository.NewsApi
import eu.tutorials.composematerialdesignsamples.domain.models.TopNewsResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NewsService(private val service: NewsApi) {

    suspend fun getArticles(country:String):TopNewsResponse = withContext(Dispatchers.IO){
        service.getTopArticles(country)
    }

    suspend fun getArticlesByCategory(category: String):TopNewsResponse= withContext(Dispatchers.IO){
        service.getArticlesByCategories(category)
    }

    suspend fun getArticleBySource(source:String):TopNewsResponse= withContext(Dispatchers.IO){
        service.getArticlesBySources(source)
    }

    suspend fun getSearchedArticles(query: String):TopNewsResponse = withContext(Dispatchers.IO){
        service.searchArticles(query)
    }
}