package eu.tutorials.composematerialdesignsamples.apptorrentmovies.views.listeners

import android.widget.ImageView

interface FavoriteListener {

    fun onDeleteFavMovie(id: Int)

    fun onMovieClicked(id: Int, imageView: ImageView)
}