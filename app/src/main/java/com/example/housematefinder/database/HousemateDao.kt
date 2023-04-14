package com.example.housematefinder.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface HousemateDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(housemate: Housemate )

    @Update
    suspend fun update( housemate: Housemate )

    @Query("SELECT * FROM housemate ORDER BY id ASC")
    fun readAllData(): LiveData<List<Housemate>>

    @Query("SELECT * FROM housemate WHERE user_id = :userid")
    suspend fun getProfile(userid:Int):Housemate?

    @Query("SELECT * FROM housemate WHERE status = 1")
    suspend fun getHousemateList():List<Housemate>

    @Query("SELECT * FROM housemate WHERE status = 1 AND gender = 'Female'")
    suspend fun getFemaleOnly():List<Housemate>
}