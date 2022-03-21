package com.afzaln.common

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ApiClient {

    private val client = HttpClient()
    private val apiHost = "http://192.168.2.199:8080/assets/schedule.json"

    fun schedule(callback: (String) -> Unit) {
        GlobalScope.launch {
            val result = client.get {
                url(apiHost)
            }

            callback(result.bodyAsText())
        }
    }

}
