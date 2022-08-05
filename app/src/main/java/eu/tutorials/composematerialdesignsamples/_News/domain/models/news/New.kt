package eu.tutorials.composematerialdesignsamples._News.domain.models.news

import eu.tutorials.composematerialdesignsamples.R

data class New(val id:Int, val image:Int = R.drawable.breaking_news, val author:String, val title:String, val description:String,val publishedAt:String)
