package com.zg.sanctuary.core.data.network

import io.ktor.client.HttpClient
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.websocket.WebSockets
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
                    explicitNulls = false
                })
            }

            // Default Options
            install(DefaultRequest){
                // TODO: - Get from build config later
                url(BASE_URL)

                header("Accept", "application/json")
                header("Content-Type", "application/json")
            }

            // Web socket
            install(WebSockets)

            // Set Timeouts
            install(HttpTimeout){
                requestTimeoutMillis = 30.seconds.inWholeMilliseconds
                connectTimeoutMillis = 30.seconds.inWholeMilliseconds
                socketTimeoutMillis = 30.seconds.inWholeMilliseconds
            }
        }
    }
}