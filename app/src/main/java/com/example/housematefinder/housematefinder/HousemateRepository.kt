package com.example.housematefinder.housematefinder

import androidx.lifecycle.LiveData
import com.example.housematefinder.database.Housemate
import com.example.housematefinder.database.HousemateDao

class HousemateRepository(private val housemateDao: HousemateDao) {
    val readAllData: LiveData<List<Housemate>> = housemateDao.readAllData()

    suspend fun addProfile(housemate: Housemate){
        housemateDao.insert(housemate)
    }

    suspend fun updateProfile(housemate: Housemate){
        housemateDao.update(housemate)
    }

    suspend fun getUserProfile(userId : Int): Housemate?{
        return housemateDao.getProfile(userId)
    }
}