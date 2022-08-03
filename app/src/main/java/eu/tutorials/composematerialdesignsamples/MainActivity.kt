package eu.tutorials.composematerialdesignsamples

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import eu.tutorials.composematerialdesignsamples.views.components.*
import eu.tutorials.composematerialdesignsamples.views.screens.Navigation.BottomMenuData
import eu.tutorials.composematerialdesignsamples.network.model.TopNewsArticle
import eu.tutorials.composematerialdesignsamples.views.screens.*
import eu.tutorials.composematerialdesignsamples.ui.theme.ComposeMaterialDesignSamplesTheme
import eu.tutorials.composematerialdesignsamples.views.screens.Navigation.setupNavGraph

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels<MainViewModel>()

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
    val openDialog = remember {
        mutableStateOf(false)
    }
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {HomeAppBar(scaffoldState, coroutineScope, openDialog)},
        drawerContent = { GmailDrawerMenu(10.dp, scrollState) },
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