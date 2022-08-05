package eu.tutorials.composematerialdesignsamples.util.listeners

import android.widget.ImageView

interface FavoriteListener {

    fun onDeleteFavMovie(id: Int)

    fun onMovieClicked(id: Int, imageView: ImageView)
}