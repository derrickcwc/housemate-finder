package com.example.housematefinder.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(user : User)

    @Update
    fun update(user : User)

    @Query("DELETE FROM user_table")
    suspend fun clearAllData()

    @Query("SELECT * FROM user_table ORDER BY userId ASC")
    suspend fun readAllData(): List<User>

    @Query("SELECT * FROM user_table WHERE username = :user AND password = :pass")
    suspend fun login(user: String, pass: String) : User?

}