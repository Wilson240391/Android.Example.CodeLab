package eu.tutorials.composematerialdesignsamples.views.screens

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.skydoves.landscapist.coil.CoilImage
import eu.tutorials.composematerialdesignsamples.Util.NewsData
import eu.tutorials.composematerialdesignsamples.Util.NewsData.getTimeAgo
import eu.tutorials.composematerialdesignsamples.R
import eu.tutorials.composematerialdesignsamples.network.model.TopNewsArticle

@Composable
fun DetailScreenApi(article: TopNewsArticle, scrollState: ScrollState,navController: NavController) {
    Scaffold(topBar = {
        DetailTopAppBarApi(onBackPressed = {navController.popBackStack()})
    }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CoilImage(
                imageModel = article.urlToImage,
                // Crop, Fit, Inside, FillHeight, FillWidth, None
                contentScale = ContentScale.Crop,
                error = ImageBitmap.imageResource(R.drawable.breaking_news),
                // shows a placeholder ImageBitmap when loading.
                placeHolder = ImageBitmap.imageResource(R.drawable.breaking_news)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                InfoWithIconApi(Icons.Default.Edit, info = article.author?:"Not Available")
                InfoWithIconApi(icon = Icons.Default.DateRange, info = NewsData.stringToDate(article.publishedAt!!).getTimeAgo())
            }
            Text(text = article.title?:"Not Available", fontWeight = FontWeight.Bold)
            Text(text = article.description?:"Not Available", modifier = Modifier.padding(top = 16.dp))
        }
    }
}

@Composable
fun DetailTopAppBarApi(onBackPressed: () -> Unit = {}) {
    TopAppBar(title = { Text(text = "Detail Screen", fontWeight = FontWeight.SemiBold) },
        navigationIcon = {
            IconButton(onClick = { onBackPressed() }) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Arrow Back")
            }
        })
}

@Composable
fun InfoWithIconApi(icon: ImageVector, info: String) {
    Row {
        Icon(icon, contentDescription = "Author", modifier = Modifier.padding(end = 8.dp), colorResource(id = R.color.purple_500))
        Text(text = info)
    }
}

@Preview(showBackground = true)
@Composable
fun DetailScreenPreviewApi() {
    DetailScreenApi(
        TopNewsArticle(
            author = "Namita Singh",
            title = "Cleo Smith news — live: Kidnap suspect 'in hospital again' as 'hard police grind' credited for breakthrough - The Independent",
            description = "The suspected kidnapper of four-year-old Cleo Smith has been treated in hospital for a second time amid reports he was “attacked” while in custody.",
            publishedAt = "2021-11-04T04:42:40Z"
        ), rememberScrollState(),
        rememberNavController()
    )
}
