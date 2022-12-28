package com.festivusofprogramming.util

import okhttp3.OkHttpClient
import org.springframework.stereotype.Component

@Component
class OkHttpProvider {
    private val client = OkHttpClient()

    fun getClient() = client
}