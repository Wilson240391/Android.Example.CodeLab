package com.example.android.devbyteviewer.network
import eu.tutorials.composematerialdesignsamples.apprepositoryvideos.network.NetworkVideoContainer
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

//A retrofit service to fetch a devbyte playlist.
interface DevbyteService {
    @GET("devbytes")
    suspend fun getPlaylist(): NetworkVideoContainer
}

//Main entry point for network access. Call like `DevByteNetwork.devbytes.getPlaylist()`
object DevByteNetwork {
    // Configure retrofit to parse JSON and use coroutines
    private val retrofit = Retrofit.Builder()
            .baseUrl("https://android-kotlin-fun-mars-server.appspot.com/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    val devbytes = retrofit.create(DevbyteService::class.java)
}


