package eu.tutorials.composematerialdesignsamples.views.screens.Navigation

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import eu.tutorials.composematerialdesignsamples.appnews.data.NewsData
import eu.tutorials.composematerialdesignsamples.views.components.*
import eu.tutorials.composematerialdesignsamples.appnews.domain.models.news.TopNewsArticle
import eu.tutorials.composematerialdesignsamples.views.screens.*
import eu.tutorials.composematerialdesignsamples.views.screens.Movies.HomeScreen
import eu.tutorials.composematerialdesignsamples.views.screens.splash.SplashScreen
import eu.tutorials.composematerialdesignsamples.views.screens.xml.Countries.NavCountries
import eu.tutorials.composematerialdesignsamples.views.screens.xml.TMDBMovies.NavTMDBMovies
import eu.tutorials.composematerialdesignsamples.views.screens.xml.TorrentMovies.NavTorrentMovies
import eu.tutorials.composematerialdesignsamples.views.screens.xml.videos.NavVideos

//import eu.tutorials.composematerialdesignsamples.views.screens.Movies.HomeScreen

@Composable
fun setupNavGraph(navController: NavHostController, scrollState: ScrollState,
                  paddingValues: PaddingValues, viewModel: MainViewModel) {
    val articles = mutableListOf(TopNewsArticle())
    val topArticle = viewModel.newsResponse.collectAsState().value.articles
    val loading by viewModel.isLoading.collectAsState()
    val error by viewModel.isError.collectAsState()
    val modifier = Modifier.padding(paddingValues)
    articles.addAll(topArticle ?: listOf(TopNewsArticle()))
    NavHost(
        navController = navController,
        startDestination = DrawerMenuData.Splash.route,
        modifier
    ) {
        val queryState = mutableStateOf(viewModel.query.value)
        val isLoading = mutableStateOf(loading)
        val isError = mutableStateOf(error)
        composable(DrawerMenuData.Splash.route) {
            SplashScreen(navController = navController)
        }
        composable(DrawerMenuData.TopNewsDummy.route) {
            TopNewsDummy(navController = navController)
        }
        composable(DrawerMenuData.TopNews.route) {
            TopNewsApi(navController = navController, articles, queryState, viewModel = viewModel, isLoading, isError)
        }
        composable(DrawerMenuData.Categories.route) {
            viewModel.getArticlesByCategory("business")
            viewModel.onSelectedCategoryChanged("business")
            Categories(viewModel = viewModel, onFetchCategory = {
                viewModel.onSelectedCategoryChanged(it)
                viewModel.getArticlesByCategory(it)
            }, isError = isError, isLoading = isLoading)
        }
        composable(DrawerMenuData.Sources.route) {
            Sources(viewModel = viewModel, isLoading, isError)
        }
        composable("DetailApi/{index}",
            arguments = listOf(
                navArgument("index") { type = NavType.IntType }
            )) { navBackStackEntry ->
            val index = navBackStackEntry.arguments?.getInt("index")
            index?.let {
                if (queryState.value != "") {
                    articles.clear()
                    articles.addAll(viewModel.searchedNewsResponse.collectAsState().value.articles ?: listOf())
                } else {
                    articles.clear()
                    articles.addAll(viewModel.newsResponse.value.articles ?: listOf())
                }
                val article = articles[index]
                DetailScreenApi(article, scrollState, navController)
            }
        }
        //Dummys
        composable("Detail/{newsId}",
            arguments = listOf(
                navArgument("newsId") { type = NavType.IntType }
            )) { navBackStackEntry ->
            val id = navBackStackEntry.arguments?.getInt("newsId")
            val newsData = NewsData.getNews(id)
            DetailScreen(newsData, scrollState, navController)
        }

        //menu options
        composable(DrawerMenuData.Mail.route) {
            MailList(paddingValues, scrollState, navController = navController)
        }
        composable(DrawerMenuData.Movies.route) {
            HomeScreen(navController, modifier)
        }
        composable(DrawerMenuData.Countries.route) {
            NavCountries()
        }
        composable(DrawerMenuData.PatternsVideo.route) {
            NavVideos()
        }
        composable(DrawerMenuData.TorrentMovies.route) {
            NavTorrentMovies()
        }
        composable(DrawerMenuData.TMDBMovies.route) {
            NavTMDBMovies()
        }

    }
}