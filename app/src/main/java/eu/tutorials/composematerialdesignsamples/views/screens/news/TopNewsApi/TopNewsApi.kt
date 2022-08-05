package eu.tutorials.composematerialdesignsamples.views.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.skydoves.landscapist.coil.CoilImage
import eu.tutorials.composematerialdesignsamples.R
import eu.tutorials.composematerialdesignsamples.views.components.SearchBar
import eu.tutorials.composematerialdesignsamples.appnews.data.NewsData
import eu.tutorials.composematerialdesignsamples.appnews.data.NewsData.getTimeAgo
import eu.tutorials.composematerialdesignsamples.appnews.domain.models.news.TopNewsArticle
import eu.tutorials.composematerialdesignsamples.views.components.ErrorUI
import eu.tutorials.composematerialdesignsamples.views.components.LoadingUI


@Composable
fun TopNewsApi(navController: NavController, articles:List<TopNewsArticle>, query: MutableState<String>,
               viewModel: MainViewModel, isLoading: MutableState<Boolean>, isError: MutableState<Boolean>
) {
    Column(modifier = Modifier.fillMaxSize(),horizontalAlignment = Alignment.CenterHorizontally) {
        SearchBar(query = query, viewModel)
        val searchedText = query.value
        val resultList = mutableListOf<TopNewsArticle>()
        if (searchedText != "") {
            resultList.addAll(viewModel.searchedNewsResponse.collectAsState().value.articles?: articles)
        }else{
            resultList.addAll(articles)
        }
        when{
            isLoading.value->{
                LoadingUI()
            }
            isError.value->{
                ErrorUI()
            }
            else->{
                LazyColumn {
                    items(resultList.size) { index ->
                        TopNewsItemApi(article = resultList[index],
                            onNewsClick = { navController.navigate("DetailApi/$index") }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TopNewsItemApi(article: TopNewsArticle, onNewsClick: () -> Unit = {},) {
    Box(modifier = Modifier
        .height(200.dp)
        .padding(8.dp)
        .clickable {
            onNewsClick()
        }) {
        CoilImage(
            imageModel = article.urlToImage,
            // Crop, Fit, Inside, FillHeight, FillWidth, None
            contentScale = ContentScale.Crop,
            error = ImageBitmap.imageResource(R.drawable.breaking_news),
            // shows a placeholder ImageBitmap when loading.
            placeHolder = ImageBitmap.imageResource(R.drawable.breaking_news),
            modifier = Modifier.fillMaxWidth()
        )
        Column(modifier = Modifier
            .wrapContentHeight()
            .padding(top = 16.dp, start = 16.dp),verticalArrangement = Arrangement.SpaceBetween) {
            Text(
                text = NewsData.stringToDate(article.publishedAt?:"2021-11-10T14:25:20Z").getTimeAgo(),
                color = Color.White,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(100.dp))
            Text(text = article.title?:"Not Available", color = Color.White, fontWeight = FontWeight.SemiBold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }}
}

@Preview(showBackground = false)
@Composable
fun TopNewsPreview() {
    TopNewsItemApi(  article =
        TopNewsArticle(
            author = "Namita Singh",
            title = "Cleo Smith news — live: Kidnap suspect 'in hospital again' as 'hard police grind' credited for breakthrough - The Independent",
            description = "The suspected kidnapper of four-year-old Cleo Smith has been treated in hospital for a second time amid reports he was “attacked” while in custody.",
            publishedAt = "2021-11-04T04:42:40Z"
        )
    )
}