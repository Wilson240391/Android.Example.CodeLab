package eu.tutorials.composematerialdesignsamples.views.screens.xml.TMDBMovies

import android.annotation.SuppressLint
import android.content.Intent
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import eu.tutorials.composematerialdesignsamples.appmoviestmdb.view.TMDBMovieActivity

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun NavTMDBMovies(){
    Scaffold() {
        val mContext = LocalContext.current
        mContext.startActivity(Intent(mContext, TMDBMovieActivity::class.java))
    }
}