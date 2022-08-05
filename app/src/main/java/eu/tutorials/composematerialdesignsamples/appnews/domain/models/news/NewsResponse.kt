package eu.tutorials.composematerialdesignsamples.appnews.domain.models.news

data class NewsResponse(val status : String? = null, val totalResults : Int? = null, val articles : List<TopNewsArticle>? = null)