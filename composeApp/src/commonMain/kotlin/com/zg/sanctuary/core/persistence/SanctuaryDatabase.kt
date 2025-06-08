package com.zg.sanctuary.core.persistence

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import com.zg.sanctuary.interests.data.persistence.InterestDao
import com.zg.sanctuary.interests.domain.Interest

@Database(
    entities = [
        Interest::class
    ],
    version = 1
)
@ConstructedBy(SanctuaryDatabaseConstructor::class)
abstract class SanctuaryDatabase : RoomDatabase() {
    abstract fun interestDao(): InterestDao

    companion object {
        const val DB_NAME = "sanctuary.db"
    }
}

@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object SanctuaryDatabaseConstructor : RoomDatabaseConstructor<SanctuaryDatabase> {
    override fun initialize(): SanctuaryDatabase
}