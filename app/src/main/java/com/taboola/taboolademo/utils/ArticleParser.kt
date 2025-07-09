package com.taboola.taboolademo.utils

import android.util.Log
import com.taboola.taboolademo.model.Article
import org.json.JSONArray

object ArticleParser {

    fun parseArticlesJson(jsonString: String): List<Article> {
        val articles = mutableListOf<Article>()
        val jsonArray = JSONArray(jsonString)

        // All three keys (name, description, thumbnail) exist in every object from the dataset.
        for (i in 0 until jsonArray.length()) {
            try {
                val item = jsonArray.getJSONObject(i)
                val name = item.optString("name", "no name")
                val description = item.optString("description", "no description")
                val thumbnail = item.optString("thumbnail", "no thumbnail")

                val article = Article(name, thumbnail, description)
                articles.add(article)
            } catch (e: Exception) {
                Log.e("ArticleParser", "Error parsing article at index: $i: ${e.message}")
                // Move on to next article
            }
        }

        return articles
    }
}