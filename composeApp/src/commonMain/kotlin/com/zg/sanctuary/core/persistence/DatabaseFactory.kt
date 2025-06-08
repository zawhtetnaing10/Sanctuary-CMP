package com.zg.sanctuary.core.persistence

import androidx.room.RoomDatabase

expect class DatabaseFactory {
    fun create(): RoomDatabase.Builder<SanctuaryDatabase>
}