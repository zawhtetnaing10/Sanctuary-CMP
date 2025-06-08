package com.zg.sanctuary.interests.domain

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Entity
data class Interest (
    @SerialName("id")
    @PrimaryKey(autoGenerate = false)
    val id : Long,

    @SerialName("name")
    val name : String,

    @SerialName("created_at")
    val createdAt : String,

    @SerialName("updated_at")
    val updatedAt : String
)