package eu.tutorials.composematerialdesignsamples._News.domain.network

import eu.tutorials.composematerialdesignsamples._News.data.NewsApi
import eu.tutorials.composematerialdesignsamples._News.domain.models.news.NewsResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NewsService(private val service: NewsApi) {

    suspend fun getArticles(country:String): NewsResponse = withContext(Dispatchers.IO){
        service.getTopArticles(country)
    }

    suspend fun getArticlesByCategory(category: String): NewsResponse = withContext(Dispatchers.IO){
        service.getArticlesByCategories(category)
    }

    suspend fun getArticleBySource(source:String): NewsResponse = withContext(Dispatchers.IO){
        service.getArticlesBySources(source)
    }

    suspend fun getSearchedArticles(query: String): NewsResponse = withContext(Dispatchers.IO){
        service.searchArticles(query)
    }
}