package eu.tutorials.composematerialdesignsamples.data.repository.news

import eu.tutorials.composematerialdesignsamples.domain.models.news.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("top-headlines")
    suspend fun getTopArticles(@Query("country") country:String): NewsResponse

    @GET("top-headlines")
    suspend fun getArticlesByCategories(@Query("category") category:String): NewsResponse

    @GET("everything")
    suspend fun getArticlesBySources(@Query("sources") source:String): NewsResponse

    @GET("everything")
    suspend fun searchArticles(@Query("q") country:String): NewsResponse
}