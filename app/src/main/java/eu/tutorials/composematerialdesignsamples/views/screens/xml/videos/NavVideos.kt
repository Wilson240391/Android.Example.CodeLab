package eu.tutorials.composematerialdesignsamples.views.screens.xml.videos

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import eu.tutorials.composematerialdesignsamples.apprepositoryvideos.views.VideosActivity

@Composable
fun NavVideos(){
    val mContext = LocalContext.current
    mContext.startActivity(Intent(mContext, VideosActivity::class.java))
}