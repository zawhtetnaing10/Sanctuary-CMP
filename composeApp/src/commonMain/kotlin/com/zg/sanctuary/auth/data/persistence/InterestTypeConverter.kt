package com.zg.sanctuary.auth.data.persistence

import androidx.room.TypeConverter
import com.zg.sanctuary.interests.domain.Interest
import kotlinx.serialization.encodeToString
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class InterestsTypeConverter {
    @TypeConverter
    fun fromInterestsList(value: List<Interest>): String {
        return Json.encodeToString(value)
    }

    @TypeConverter
    fun toInterestsList(value: String): List<Interest> {
        return Json.decodeFromString(value)
    }
}