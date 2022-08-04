package eu.tutorials.composematerialdesignsamples.domain.models.mail

data class Mail(val mailId:Int, val userName:String, val subject:String, val body:String, val timeStamp:String="")