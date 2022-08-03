package eu.tutorials.composematerialdesignsamples.views.screens.countries

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import eu.tutorials.composematerialdesignsamples.views.activitys.CountryActivity

@Composable
fun NavCountries(){
    val mContext = LocalContext.current
    mContext.startActivity(Intent(mContext, CountryActivity::class.java))
}