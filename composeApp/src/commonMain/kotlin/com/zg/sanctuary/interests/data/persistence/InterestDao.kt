package com.zg.sanctuary.interests.data.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zg.sanctuary.interests.domain.Interest

@Dao
interface InterestDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInterests(interests : List<Interest>)

    @Query("SELECT * FROM Interest")
    suspend fun getAllInterests() : List<Interest>
}