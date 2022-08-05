package eu.tutorials.composematerialdesignsamples

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import dagger.hilt.android.AndroidEntryPoint
import eu.tutorials.composematerialdesignsamples.appmovies.domain.Movie
import eu.tutorials.composematerialdesignsamples.appmovies.domain.MovieGenre
import eu.tutorials.composematerialdesignsamples.views.components.*
import eu.tutorials.composematerialdesignsamples.views.screens.*
import eu.tutorials.composematerialdesignsamples.ui.theme.ComposeMaterialDesignSamplesTheme
import eu.tutorials.composematerialdesignsamples.views.screens.Movies.*
import eu.tutorials.composematerialdesignsamples.views.screens.Navigation.DrawerMenuData
import eu.tutorials.composematerialdesignsamples.views.screens.Navigation.setupNavGraph
import java.util.*

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels<MainViewModel>()
    private val homeViewModel: HomeViewModel by viewModels<HomeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getTopArticles()
        setContent {
            ComposeMaterialDesignSamplesTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    MainScreen(viewModel)
                }
            }
        }
    }
}

@Composable
fun MainScreen(viewModel: MainViewModel) {
    var scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    val scrollState = rememberScrollState()
    val navController = rememberNavController()
    val scope = rememberCoroutineScope()
    val openDialog = remember {
        mutableStateOf(false)
    }
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {HomeAppBar(scaffoldState, coroutineScope, openDialog)},
        drawerContent = { GmailDrawerMenu(10.dp, scrollState, navController = navController, scaffoldState, scope) },
        bottomBar = { BottomMenu(navController = navController) },
        floatingActionButton = {GmailFab(scrollState)}
        ) {
        setupNavGraph(navController, scrollState = scrollState, it, viewModel)
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeMaterialDesignSamplesTheme {
        MainScreen(viewModel())
    }
}

