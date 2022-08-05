package eu.tutorials.composematerialdesignsamples.views.screens.xml.TorrentMovies

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import eu.tutorials.composematerialdesignsamples.appcountries.views.CountryActivity

@Composable
fun NavTorrentMovies(){
    val mContext = LocalContext.current
    mContext.startActivity(Intent(mContext, CountryActivity::class.java))
}