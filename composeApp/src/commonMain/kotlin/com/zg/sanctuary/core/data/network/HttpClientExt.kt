package com.zg.sanctuary.core.data.network

import com.zg.sanctuary.core.domain.SanctuaryError
import com.zg.sanctuary.core.domain.SanctuaryErrorEnums
import io.ktor.client.call.body
import io.ktor.client.network.sockets.SocketTimeoutException
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.coroutines.ensureActive
import kotlinx.serialization.SerializationException
import kotlin.coroutines.coroutineContext

suspend inline fun <reified T> safeCall(
    execute: () -> HttpResponse
): SanctuaryResult<T, SanctuaryError> {
    val response = try {
        execute()
    } catch(e: SocketTimeoutException) {
        return SanctuaryResult.Failure(SanctuaryError(error = e.message ?: "Socket time out", errorType = SanctuaryErrorEnums.Remote.SOCKET_TIMEOUT))
    } catch(e: UnresolvedAddressException) {
        return SanctuaryResult.Failure(SanctuaryError(error = e.message ?: "No internet.", errorType = SanctuaryErrorEnums.Remote.NO_INTERNET))
    } catch (e: Exception) {
        coroutineContext.ensureActive()
        return SanctuaryResult.Failure(SanctuaryError(error = e.message ?: "Unknown error.", errorType = SanctuaryErrorEnums.Remote.UNKNOWN))
    }

    return responseToResult(response)
}

suspend inline fun <reified T> responseToResult(httpResponse: HttpResponse): SanctuaryResult<T, SanctuaryError> {
    return when (httpResponse.status.value) {
        in 200..299 -> {
            try{
                return SanctuaryResult.Success(httpResponse.body<T>())
            } catch(e : SerializationException) {
                return SanctuaryResult.Failure(
                    SanctuaryError(
                        errorType = SanctuaryErrorEnums.Remote.JSON_PARSE_ERROR,
                        error = "Failed to parse successful response data: ${e.message}. Raw: ${httpResponse.bodyAsText()}"
                    )
                )
            } catch (e: Exception) {
                return SanctuaryResult.Failure(
                    SanctuaryError(
                        errorType = SanctuaryErrorEnums.Remote.UNKNOWN,
                        error = "An unexpected error occurred during success body processing: ${e.message}. Raw: ${httpResponse.bodyAsText()}"
                    )
                )
            }
        }

        // Client Errors (4xx)
        400 -> SanctuaryResult.Failure(responseToError(httpResponse, SanctuaryErrorEnums.Remote.BAD_REQUEST))
        401 -> SanctuaryResult.Failure(responseToError(httpResponse, SanctuaryErrorEnums.Remote.UNAUTHORIZED))
        403 -> SanctuaryResult.Failure(responseToError(httpResponse, SanctuaryErrorEnums.Remote.FORBIDDEN))
        404 -> SanctuaryResult.Failure(responseToError(httpResponse, SanctuaryErrorEnums.Remote.NOT_FOUND))
        408 -> SanctuaryResult.Failure(responseToError(httpResponse, SanctuaryErrorEnums.Remote.REQUEST_TIMEOUT))
        415 -> SanctuaryResult.Failure(responseToError(httpResponse, SanctuaryErrorEnums.Remote.UNSUPPORTED_MEDIA_TYPE))
        429 -> SanctuaryResult.Failure(responseToError(httpResponse, SanctuaryErrorEnums.Remote.TOO_MANY_REQUESTS))

        // Server Errors (5xx)
        500 -> SanctuaryResult.Failure(responseToError(httpResponse, SanctuaryErrorEnums.Remote.SERVER_ERROR))
        502 -> SanctuaryResult.Failure(responseToError(httpResponse, SanctuaryErrorEnums.Remote.BAD_GATEWAY))
        504 -> SanctuaryResult.Failure(responseToError(httpResponse, SanctuaryErrorEnums.Remote.GATEWAY_TIMEOUT))

        else -> {
            return SanctuaryResult.Failure(SanctuaryError(errorType = SanctuaryErrorEnums.Remote.SERVER_ERROR, error = "Unknown error"))
        }
    }
}

suspend inline fun responseToError(httpResponse: HttpResponse, errorType : SanctuaryErrorEnums): SanctuaryError {
    try {
        val error =  httpResponse.body<SanctuaryError>()
        error.errorType = errorType
        return error
    } catch (e: SerializationException) {
        return SanctuaryError(errorType = SanctuaryErrorEnums.Remote.JSON_PARSE_ERROR, error = e.message ?: "Unknown error")
    } catch (e: Exception) {
        return SanctuaryError(errorType = SanctuaryErrorEnums.Remote.UNKNOWN, error = e.message ?: "Unknown error")
    }
}