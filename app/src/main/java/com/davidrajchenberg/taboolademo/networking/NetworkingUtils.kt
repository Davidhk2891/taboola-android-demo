package com.davidrajchenberg.taboolademo.networking

import java.net.HttpURLConnection
import java.net.URL
import java.io.BufferedReader
import java.io.InputStreamReader

object NetworkingUtils {

    fun fetchArticlesJson(): String {
        val urlString = "https://s3-us-west-2.amazonaws.com/taboola-mobile-sdk/public/home_assignment/data.json"
        val url = URL(urlString)
        val urlConnection = url.openConnection() as HttpURLConnection

        try {
            urlConnection.requestMethod = "GET"
            urlConnection.connectTimeout = 5000
            urlConnection.readTimeout = 5000

            val responseCode = urlConnection.responseCode
            if (responseCode != HttpURLConnection.HTTP_OK) {
                throw Exception("HTTP error code: $responseCode")
            }

            val inputStream = urlConnection.inputStream
            val inputStreamReader = InputStreamReader(inputStream)
            val reader = BufferedReader(inputStreamReader)
            val response = StringBuilder()
            var line: String? = reader.readLine()

            while (line != null) {
                response.append(line)
                line = reader.readLine()
            }

            reader.close()
            return response.toString()
        } finally {
            urlConnection.disconnect()
        }
    }
}