package com.zg.sanctuary.core.domain

sealed interface SanctuaryErrorEnums {
    enum class Remote : SanctuaryErrorEnums {
        SERVER_ERROR,
        NOT_FOUND,
        BAD_REQUEST,
        UNAUTHORIZED,
        SOCKET_TIMEOUT,
        FORBIDDEN,
        REQUEST_TIMEOUT,
        TOO_MANY_REQUESTS,
        UNSUPPORTED_MEDIA_TYPE,
        BAD_GATEWAY,
        GATEWAY_TIMEOUT,
        NO_INTERNET,
        JSON_PARSE_ERROR,
        UNKNOWN
    }

    enum class Local : SanctuaryErrorEnums {
        DISK_FULL,
        UNKNOWN
    }
}