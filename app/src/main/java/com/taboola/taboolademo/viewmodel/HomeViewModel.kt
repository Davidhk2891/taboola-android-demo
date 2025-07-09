package com.taboola.taboolademo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.taboola.taboolademo.model.Article
import com.taboola.taboolademo.networking.NetworkingUtils
import com.taboola.taboolademo.utils.ArticleParser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val _articles = MutableStateFlow<List<Article>>(emptyList())
    val articles: StateFlow<List<Article>> get() = _articles

    init {
        fetchArticles()
    }

    private fun fetchArticles() {
        viewModelScope.launch(Dispatchers.IO) {
            val json = NetworkingUtils.fetchArticlesJson()
            val parsedArticles = ArticleParser.parseArticlesJson(json)
            _articles.value = parsedArticles
        }
    }
}