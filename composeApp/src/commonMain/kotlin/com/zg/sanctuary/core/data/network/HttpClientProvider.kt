package com.zg.sanctuary.core.data.network

import io.ktor.client.HttpClient
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.header
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import kotlin.time.Duration.Companion.seconds

object HttpClientProvider {
    val httpClient by lazy {
        HttpClient{
            // Json serialization
            install(ContentNegotiation){
                json(Json {
                    isLenient = true
                    prettyPrint = true
                    ignoreUnknownKeys = true
                    useAlternativeNames = false
                })
            }

            // Default Options
            install(DefaultRequest){
                // TODO: - Get from build config later
                url("http://localhost:8080")

                header("Accept", "application/json")
                header("Content-Type", "application/json")
            }

            // Set Timeouts
            install(HttpTimeout){
                requestTimeoutMillis = 30.seconds.inWholeMilliseconds
                connectTimeoutMillis = 30.seconds.inWholeMilliseconds
                socketTimeoutMillis = 30.seconds.inWholeMilliseconds
            }
        }
    }
}