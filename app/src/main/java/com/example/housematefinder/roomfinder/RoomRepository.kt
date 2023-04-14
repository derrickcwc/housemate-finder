package com.example.housematefinder.roomfinder

import androidx.lifecycle.LiveData
import com.example.housematefinder.database.RoomList
import com.example.housematefinder.database.RoomListDao

class RoomRepository(private val roomListDao: RoomListDao) {

    val readAllData: LiveData<List<RoomList>> = roomListDao.readAllData()

    suspend fun addRoom(roomList: RoomList){
        roomListDao.insert(roomList)
    }

    suspend fun updateRoom(roomList: RoomList){
        roomListDao.update(roomList)
    }
}