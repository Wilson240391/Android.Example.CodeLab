package eu.tutorials.composematerialdesignsamples.domain.models

import eu.tutorials.composematerialdesignsamples.network.model.TopNewsArticle

data class TopNewsResponse(val status : String? = null, val totalResults : Int? = null, val articles : List<TopNewsArticle>? = null)