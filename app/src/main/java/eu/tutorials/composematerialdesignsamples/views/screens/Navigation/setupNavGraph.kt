package eu.tutorials.composematerialdesignsamples.views.screens.Navigation

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import eu.tutorials.composematerialdesignsamples.Util.NewsData
import eu.tutorials.composematerialdesignsamples.views.components.*
import eu.tutorials.composematerialdesignsamples.network.model.TopNewsArticle
import eu.tutorials.composematerialdesignsamples.views.screens.*
import eu.tutorials.composematerialdesignsamples.views.screens.Countries.NavCountries

@Composable
fun setupNavGraph(navController: NavHostController, scrollState: ScrollState,
                  paddingValues: PaddingValues, viewModel: MainViewModel) {
    val articles = mutableListOf(TopNewsArticle())
    val topArticle = viewModel.newsResponse.collectAsState().value.articles
    val loading by viewModel.isLoading.collectAsState()
    val error by viewModel.isError.collectAsState()
    articles.addAll(topArticle ?: listOf(TopNewsArticle()))
    NavHost(
        navController = navController,
        startDestination = BottomMenuData.Categories.route,
        modifier = Modifier.padding(paddingValues = paddingValues)
    ) {
        val queryState = mutableStateOf(viewModel.query.value)
        val isLoading = mutableStateOf(loading)
        val isError = mutableStateOf(error)
        composable(BottomMenuData.TopNewsDummy.route) {
            TopNewsDummy(navController = navController)
        }
        composable(BottomMenuData.TopNewsApi.route) {
            TopNewsApi(navController = navController, articles, queryState, viewModel = viewModel, isLoading, isError)
        }
        composable(BottomMenuData.Categories.route) {
            viewModel.getArticlesByCategory("business")
            viewModel.onSelectedCategoryChanged("business")
            Categories(viewModel = viewModel, onFetchCategory = {
                viewModel.onSelectedCategoryChanged(it)
                viewModel.getArticlesByCategory(it)
            }, isError = isError, isLoading = isLoading)
        }
        composable(BottomMenuData.Sources.route) {
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
        composable(BottomMenuData.Countries.route) {
            NavCountries()
        }
        composable(BottomMenuData.MailList.route) {
            MailList(paddingValues, scrollState, navController = navController)
        }
    }
}