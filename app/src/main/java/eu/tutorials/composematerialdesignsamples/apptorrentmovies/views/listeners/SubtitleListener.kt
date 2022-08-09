package eu.tutorials.composematerialdesignsamples.apptorrentmovies.views.listeners

import com.masterwok.opensubtitlesandroid.models.OpenSubtitleItem

interface SubtitleListener {

    fun onSubtitleClicked(subtitle: OpenSubtitleItem)
}