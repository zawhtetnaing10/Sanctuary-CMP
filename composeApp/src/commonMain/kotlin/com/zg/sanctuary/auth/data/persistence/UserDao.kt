package com.zg.sanctuary.auth.data.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zg.sanctuary.auth.domain.User

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveUser(user: User)

    @Query("SELECT * FROM User LIMIT 1")
    suspend fun getLoggedInUser() : User?

    @Query("DELETE FROM User")
    suspend fun deleteAllUsers()
}