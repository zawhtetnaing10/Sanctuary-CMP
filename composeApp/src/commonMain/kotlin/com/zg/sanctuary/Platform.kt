package com.zg.sanctuary

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform