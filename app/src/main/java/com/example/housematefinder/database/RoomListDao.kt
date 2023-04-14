package com.example.housematefinder.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface RoomListDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(roomList : RoomList)

    @Update
    suspend fun update(roomList : RoomList)

    @Query("SELECT * FROM room_list ORDER BY id ASC")
    fun readAllData(): LiveData<List<RoomList>>

    @Query("SELECT * FROM room_list WHERE user_id = :userid")
    suspend fun getProfile(userid:Int):RoomList?

    @Query("SELECT * FROM room_list WHERE status = 1")
    suspend fun getRoomList():List<RoomList>

    @Query("SELECT * FROM room_list WHERE status = 1 AND female_only = 1")
    suspend fun getFemaleOnly():List<RoomList>
}