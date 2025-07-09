package com.taboola.taboolademo.view

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.taboola.taboolademo.ui.theme.TaboolaDemoTheme
import com.taboola.taboolademo.viewmodel.HomeViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TaboolaDemoTheme {
                val navController = rememberNavController()
                val homeViewModel: HomeViewModel = viewModel()
                
                NavHost(navController = navController, startDestination = "home") {
                    composable("home") {
                        ArticleListScreen(
                            viewModel = homeViewModel,
                            onClick = { index ->
                                navController.navigate("detail/$index")
                            }
                        )
                    }
                    composable("detail/{index}") { backStackEntry ->
                        val index = backStackEntry.arguments?.getString("index")?.toIntOrNull()
                        val article = index?.let { homeViewModel.articles.value.getOrNull(it) }

                        if (article != null) {
                            ArticleDetailScreen(article = article)
                        }
                    }
                }
            }
        }
    }
}