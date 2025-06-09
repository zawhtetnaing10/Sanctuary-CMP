package com.zg.sanctuary.posts.domain

import com.zg.sanctuary.comments.domain.Comment
import com.zg.sanctuary.auth.domain.User

data class Post(
    val id : Int,
    val content : String,
    val user : User,
    val createdAt : String,
    val updatedAt : String,
    val deletedAt : String?,
    val comments : List<Comment>?,
    val numComments : Int,
    val numLikes : Int,
)