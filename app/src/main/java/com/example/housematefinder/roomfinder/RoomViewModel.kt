package com.example.housematefinder.roomfinder

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.housematefinder.database.Housemate
import com.example.housematefinder.database.RoomList
import com.example.housematefinder.database.UserDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class RoomViewModel(application: Application): AndroidViewModel(application) {
    private val readAllData: LiveData<List<RoomList>>
    private val repository: RoomRepository

    init{
        val roomListDao = UserDatabase.getDatabase(application).roomListDao()
        repository = RoomRepository(roomListDao)
        readAllData = repository.readAllData
    }

    fun addRoom (roomList: RoomList){
        viewModelScope.launch (Dispatchers.IO){
            repository.addRoom(roomList)
        }
    }

    fun updateRoom (roomList: RoomList){
        viewModelScope.launch (Dispatchers.IO){
            repository.updateRoom(roomList)
        }
    }
}