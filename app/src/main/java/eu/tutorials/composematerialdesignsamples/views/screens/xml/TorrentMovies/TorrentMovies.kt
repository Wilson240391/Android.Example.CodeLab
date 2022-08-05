package eu.tutorials.composematerialdesignsamples.views.screens.xml.TorrentMovies

import android.annotation.SuppressLint
import android.content.Intent
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import eu.tutorials.composematerialdesignsamples.appcountries.views.CountryActivity
import eu.tutorials.composematerialdesignsamples.apptorrentmovies.views.TorrentActivity

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun NavTorrentMovies(){
    Scaffold() {
        val mContext = LocalContext.current
        mContext.startActivity(Intent(mContext, TorrentActivity::class.java))
    }
}