package com.davidrajchenberg.taboolademo.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.davidrajchenberg.taboolademo.ui.theme.TaboolaDemoTheme
import com.davidrajchenberg.taboolademo.viewmodel.HomeViewModel
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