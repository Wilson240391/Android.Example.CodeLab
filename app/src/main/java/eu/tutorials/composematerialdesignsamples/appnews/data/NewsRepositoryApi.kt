package eu.tutorials.composematerialdesignsamples.appnews.data

import eu.tutorials.composematerialdesignsamples.appnews.domain.network.NewsService

class RepositoryApi(val manager: NewsService) {
    suspend fun getArticles() = manager.getArticles("us")
    suspend fun getArticleByCategory(category:String) = manager.getArticlesByCategory(category)
    suspend fun getArticlesBySource(source:String) = manager.getArticleBySource(source = source)
    suspend fun getSearchedArticles(query:String) = manager.getSearchedArticles(query = query)

}