package eu.tutorials.composematerialdesignsamples._News.domain.models.news

data class NewsResponse(val status : String? = null, val totalResults : Int? = null, val articles : List<TopNewsArticle>? = null)