package com.zg.sanctuary.core.network

sealed interface SanctuaryResult<out D, out E> {
    data class Success<out D>(val data: D) : SanctuaryResult<D, Nothing>
    data class Failure<out E>(val error: E) : SanctuaryResult<Nothing, E>
}

inline fun <D, E> SanctuaryResult<D, E>.onSuccess(action: (D) -> Unit): SanctuaryResult<D, E> {
    return when (this) {
        is SanctuaryResult.Failure -> this
        is SanctuaryResult.Success -> {
            action(this.data)
            this
        }
    }
}

inline fun <D, E> SanctuaryResult<D, E>.onError(errorAction: (E) -> Unit): SanctuaryResult<D, E> {
    return when (this) {
        is SanctuaryResult.Failure -> {
            errorAction(this.error)
            this
        }
        is SanctuaryResult.Success -> this
    }
}