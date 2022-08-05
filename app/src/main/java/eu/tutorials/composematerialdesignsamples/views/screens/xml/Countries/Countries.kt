package eu.tutorials.composematerialdesignsamples.views.screens.xml.Countries

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import eu.tutorials.composematerialdesignsamples._Countries.views.CountryActivity

@Composable
fun NavCountries(){
    val mContext = LocalContext.current
    mContext.startActivity(Intent(mContext, CountryActivity::class.java))
}