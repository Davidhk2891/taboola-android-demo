package com.taboola.taboolademo.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.taboola.taboolademo.model.Article
import com.taboola.taboolademo.ui.theme.TaboolaDemoTheme
import com.taboola.taboolademo.viewmodel.HomeViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TaboolaDemoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ArticleListScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun ArticleListScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel()
) {
    val articleList by viewModel.articles.collectAsState()
    // ArticleListScreenMock(articleList = articleList, modifier = modifier)
    LazyColumn(modifier = modifier) {
        items(10) { index ->
            if (index == 2 || index == 9) {
                Spacer(modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp))
            } else {
                val article = articleList.getOrNull(index)
                if (article != null) {
                    ArticleItem(article)
                }
            }
        }
    }

}

@Composable
fun ArticleItem(article: Article, modifier: Modifier = Modifier) {
    Card(
      modifier = modifier
          .fillMaxWidth()
          .height(160.dp) // Fixed height for consistency across cards
          .padding(horizontal = 16.dp, vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = article.name,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = article.description,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 4, //Max lines for description if these are large
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

// Mock for previewing UI
@Composable
fun ArticleListScreenMock(
    articleList: List<Article>,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        items(10) { index ->
            if (index == 2 || index == 9) {
                Spacer(modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp))
            } else {
                val article = articleList.getOrNull(index)
                if (article != null) {
                    ArticleItem(article)
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
    ArticleListScreenMock(articleList = dummyArticles)
}