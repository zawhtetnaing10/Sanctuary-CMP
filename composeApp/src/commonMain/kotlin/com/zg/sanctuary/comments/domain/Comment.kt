package com.zg.sanctuary.comments.domain

data class Comment(
    val id: Int,
    val content: String,
    val createdAt: String,
    val updatedAt: String,
    val deletedAt: String?
)