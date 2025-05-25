package com.zg.sanctuary.user.domain

data class User (
    val id: Int,
    val email : String,
    val userName: String,
    val fullName: String,
    val createdAt : String,
    val updatedAt : String,
    val deletedAt : String?,
    val profileImageUrl : String?,
    val dob : String?,
)