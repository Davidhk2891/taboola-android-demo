package com.taboola.taboolademo.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.glide.GlideImage
import com.taboola.taboolademo.R
import com.taboola.taboolademo.model.Article

@Composable
fun ArticleDetailScreen(article: Article) {
    Column(modifier = Modifier
        .fillMaxSize()
    ) {
        GlideImage(
            imageModel = { article.thumbnail },
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp),
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
        Column(
            modifier = Modifier.
            fillMaxSize()
                .padding(16.dp)
        ) {
            Text(text = article.name, style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = article.description)
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}