package eu.tutorials.composematerialdesignsamples.views.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import eu.tutorials.composematerialdesignsamples.appnews.domain.models.news.TopNewsArticle
import eu.tutorials.composematerialdesignsamples.R
import eu.tutorials.composematerialdesignsamples.views.components.ErrorUI
import eu.tutorials.composematerialdesignsamples.views.components.LoadingUI

@Composable
fun Sources(viewModel: MainViewModel, isLoading: MutableState<Boolean>, isError: MutableState<Boolean>) {
    val items = listOf("List" to "techcrunch", "Games" to "talksport", "Algorim" to "sabq",
        "Logical" to "reuters", "numbers" to "politico", "calculate" to "verge"
    )
    Scaffold(topBar={
        TopAppBar(
            //title = { Text(text = "${viewModel.sourceName.value} Source") },
            title = { Text(text = "Examples CodeLabs") },
            actions = {
                var menuExpanded by remember { mutableStateOf(false) }
                IconButton(onClick = { menuExpanded = true }) {
                    Icon(Icons.Default.MoreVert, contentDescription = null)
                }
                MaterialTheme(shapes = MaterialTheme.shapes.copy(medium = RoundedCornerShape(16.dp))) {
                    DropdownMenu(
                        expanded = menuExpanded,
                        onDismissRequest = {
                            menuExpanded = false
                        },
                    ) {
                        items.forEach {
                            DropdownMenuItem(onClick = {
                                viewModel.sourceName.value = it.second
                                viewModel.getArticleBySource()
                                menuExpanded = false
                            }) {
                                Text(it.first)
                            }
                        }
                    }
                }
            }
        )
    }){
        when {
            isLoading.value -> {
                LoadingUI()
            }
            isError.value -> {
                ErrorUI()
            }
            else -> {
                viewModel.getArticleBySource()
                val article = viewModel.getArticleBySource.collectAsState().value
                SourceContent(articles = article.articles?: listOf() )
            }
        }
    }
}

@Composable
fun SourceContent(articles:List<TopNewsArticle>) {
    val uriHandler = LocalUriHandler.current
    LazyColumn{
        items(articles.size) {
            val article = articles[it]
            val annotatedString = buildAnnotatedString {
                pushStringAnnotation(
                    tag = "URL",
                    annotation = article.url ?: "newsapi.org"
                )
                withStyle(style = SpanStyle(color = colorResource(id = R.color.purple_500),textDecoration = TextDecoration.Underline)) {
                    append("Make a Example")
                }
                pop()
            }
            Card(elevation = 6.dp, modifier = Modifier.padding(8.dp),border = BorderStroke(2.dp,color = colorResource(id = R.color.purple_500))) {
                Column(modifier = Modifier
                    .height(200.dp)
                    .padding(end = 8.dp, start = 8.dp),verticalArrangement = Arrangement.SpaceEvenly) {
                    Text(
                        text = article.title ?: "Not Available",
                        fontWeight = FontWeight.Bold,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = article.description ?: "Not Available",
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis
                    )

                    Card(
                        backgroundColor = colorResource(id = R.color.white),
                        elevation = 6.dp,
                    ) {
                        ClickableText(text = annotatedString,
                            modifier = Modifier.padding(8.dp),
                            onClick = {
                                annotatedString.getStringAnnotations(it,it).firstOrNull()
                                    ?.let { result ->
                                        if (result.tag == "URL") {
                                            uriHandler.openUri(result.item)
                                        }
                                    }
                            })
                    }
                }
            }
        }}
}

@Preview(showBackground = true)
@Composable
fun SourceContentPreview() {
    SourceContent(articles = listOf(
        TopNewsArticle(
        author = "Namita Singh",
        title = "Cleo Smith news — live: Kidnap suspect 'in hospital again' as 'hard police grind' credited for breakthrough - The Independent",
        description = "The suspected kidnapper of four-year-old Cleo Smith has been treated in hospital for a second time amid reports he was “attacked” while in custody.",
        publishedAt = "2021-11-04T04:42:40Z"
    )

    ))
}