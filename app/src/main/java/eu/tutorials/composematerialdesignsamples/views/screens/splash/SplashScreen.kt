package eu.tutorials.composematerialdesignsamples.views.screens.splash

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import eu.tutorials.composematerialdesignsamples.ui.theme.Purple500
import eu.tutorials.composematerialdesignsamples.ui.theme.Purple700
import eu.tutorials.composematerialdesignsamples.views.screens.Navigation.DrawerMenuData
import eu.tutorials.composematerialdesignsamples.R

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SplashScreen(
    navController: NavHostController,
) {
    Scaffold() {
        val degrees = remember { Animatable(0f) }
        LaunchedEffect(key1 = true) {
            degrees.animateTo(
                targetValue = 360f,
                animationSpec = tween(
                    durationMillis = 1000,
                    delayMillis = 200
                )
            )
            navController.popBackStack()
            navController.navigate(DrawerMenuData.Categories.route)
        }
        Splash(degrees = degrees.value)
    }
}

@Composable
fun Splash(degrees: Float) {
    val modifier = if (isSystemInDarkTheme()) {
        Modifier.background(Color.Black)
    } else {
        Modifier.background(
            Brush.verticalGradient(listOf(Purple700, Purple500))
        )
    }
    Box(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier.rotate(degrees = degrees),
            painter = painterResource(id = R.drawable.ic_logo),
            contentDescription = stringResource(R.string.app_name)
        )
    }

}

@Composable
@Preview
fun SplashScreenPreview() {
    Splash(degrees = 0f)
}

@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
fun SplashScreenDarkPreview() {
    Splash(degrees = 0f)
}