package eu.tutorials.composematerialdesignsamples.domain.models

import eu.tutorials.composematerialdesignsamples.R

data class New(val id:Int, val image:Int = R.drawable.breaking_news, val author:String, val title:String, val description:String,val publishedAt:String)
