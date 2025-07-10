package com.davidrajchenberg.taboolademo.view

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.clickable
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.skydoves.landscapist.glide.GlideImage
import com.davidrajchenberg.taboolademo.R
import com.davidrajchenberg.taboolademo.model.Article
import com.davidrajchenberg.taboolademo.viewmodel.HomeViewModel
import com.taboola.android.Taboola
import com.taboola.android.annotations.TBL_PLACEMENT_TYPE
import com.taboola.android.listeners.TBLClassicListener
import com.davidrajchenberg.taboolademo.utils.TaboolaConstants

@Composable
fun ArticleListScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(),
    onClick: (Int) -> Unit
) {
    val context = LocalContext.current

    val articleList by viewModel.articles.collectAsState()

    // Setup Taboola page url to fetch content from
    val tblClassicPage = remember {
        Taboola.getClassicPage(
            TaboolaConstants.PAGE_URL,
            TaboolaConstants.PAGE_TYPE_ARTICLE
        )
    }

    // Setup classic widget unit
    val classicWidgetUnit = remember {
        tblClassicPage.buildComposeUnit(
            context,
            TaboolaConstants.PLACEMENT_WIDGET_WO_VIDEO,
            TaboolaConstants.MODE_THUMBS_FEED_01,
            TBL_PLACEMENT_TYPE.PAGE_MIDDLE,
            object : TBLClassicListener() {
                override fun onAdReceiveSuccess() {
                    Log.d("Taboola classic unit", "classic unit Ad received successfully")
                }

                override fun onAdReceiveFail(error: String?) {
                    Log.e("Taboola classic unit", "classic unit Ad failed: $error")
                }
            }
        )
    }

    // Setup feed unit
    /*
    placementName: Feed without video, Feed with video
    mode: thumbs-feed-01, thumbnails-feed, feed-3x1
    */
    val feedUnit = remember {
        tblClassicPage.buildComposeUnit(
            context,
            TaboolaConstants.PLACEMENT_FEED_W_VIDEO,
            TaboolaConstants.MODE_THUMBS_FEED_01,
            TBL_PLACEMENT_TYPE.FEED,
            object: TBLClassicListener() {
                override fun onAdReceiveSuccess() {
                    Log.d("Taboola feed unit", "feed unit Ad received successfully")
                }

                override fun onAdReceiveFail(error: String?) {
                    Log.e("Taboola feed unit", "feed unit Ad failed: $error")
                }
            }
        )
    }

    // Fetch content for both units
    LaunchedEffect(Unit) {
        classicWidgetUnit.fetchContent()
        feedUnit.fetchContent()
    }

    // ArticleListScreenMock(articleList = articleList, modifier = modifier)
    LazyColumn(modifier = modifier) {
        itemsIndexed(articleList) { index, article ->
            when (index) {
                2 -> {
                    Box(modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp, vertical = 8.dp)) {
                        classicWidgetUnit.GetClassicUnitView()
                    }
                }
                9 -> {
                    Box(modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp, vertical = 8.dp)) {
                        feedUnit.GetClassicUnitView()
                    }
                }
                else -> {
                    ArticleItem(
                        article = article,
                        onClick = { onClick(index) }
                    )
                }
            }
        }
    }
}

@Composable
fun ArticleItem(article: Article, onClick: () -> Unit , modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(260.dp) // Fixed height for consistency across cards
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier
            .fillMaxSize()
        ) {
            // Load thumbnail
            GlideImage(
                imageModel = { article.thumbnail },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                loading = {
                    // Placeholder while image loads
                    Text(
                        text = "Loading...",
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp)
                            .wrapContentSize()
                    )
                },
                failure = {
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.baseline_broken_image_24),
                            contentDescription = "Error loading resource",
                            modifier = Modifier
                                .size(64.dp),
                            contentScale = ContentScale.Fit
                        )
                    }
                }
            )
            Column(modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = article.name,
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = article.description,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 4, //Max lines for description if these are large
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

// Mock for previewing UI
@Composable
fun ArticleListScreenMock(
    articleList: List<Article>,
    onClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        itemsIndexed(articleList) { index, article ->
            when (index) {
                2, 9 -> {
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp)
                    )
                }
                else -> {
                    ArticleItem(
                        article = article,
                        onClick = { onClick(index) }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ArticleListScreenMockPreview() {
    val dummyArticles = List(10) {
        Article(
            name = "Sample title $it",
            description = "Sample Description $it",
            thumbnail = ""
        )
    }
    ArticleListScreenMock(articleList = dummyArticles, onClick = {})
}