package eu.tutorials.composematerialdesignsamples.util.torrents.listeners

import com.masterwok.opensubtitlesandroid.models.OpenSubtitleItem

interface SubtitleListener {

    fun onSubtitleClicked(subtitle: OpenSubtitleItem)
}