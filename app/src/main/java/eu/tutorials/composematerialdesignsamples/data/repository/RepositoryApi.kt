package eu.tutorials.composematerialdesignsamples.data.repository

import eu.tutorials.composematerialdesignsamples.domain.network.NewsManager
import javax.xml.transform.Source

class RepositoryApi(val manager: NewsManager) {
    suspend fun getArticles() = manager.getArticles("us")
    suspend fun getArticleByCategory(category:String) = manager.getArticlesByCategory(category)
    suspend fun getArticlesBySource(source:String) = manager.getArticleBySource(source = source)
    suspend fun getSearchedArticles(query:String) = manager.getSearchedArticles(query = query)

}