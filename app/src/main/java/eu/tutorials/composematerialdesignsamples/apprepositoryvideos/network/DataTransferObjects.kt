package eu.tutorials.composematerialdesignsamples.apprepositoryvideos.network

import com.example.android.devbyteviewer.database.DatabaseVideo
import com.squareup.moshi.JsonClass

//This is to parse first level of our network result which looks like
@JsonClass(generateAdapter = true)
data class NetworkVideoContainer(val videos: List<NetworkVideo>)

//Videos represent a devbyte that can be played.
@JsonClass(generateAdapter = true)
data class NetworkVideo(val title: String, val description: String, val url: String, val updated: String, val thumbnail: String,
                        val closedCaptions: String?)

//Convert Network results to database objects
fun NetworkVideoContainer.asDomainModel(): List<DevByteVideo> {
    return videos.map { DevByteVideo(title = it.title, description = it.description, url = it.url, updated = it.updated,
        thumbnail = it.thumbnail) }
}

//Convert Network results to database objects
fun NetworkVideoContainer.asDatabaseModel(): List<DatabaseVideo> {
    return videos.map { DatabaseVideo(title = it.title, description = it.description, url = it.url, updated = it.updated,
        thumbnail = it.thumbnail) }
}

