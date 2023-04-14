package com.example.housematefinder.housematefinder

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.housematefinder.database.Housemate
import com.example.housematefinder.database.UserDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class HousemateViewModel(application: Application): AndroidViewModel(application)  {

    private val readAllData: LiveData<List<Housemate>>
    private val repository: HousemateRepository
    private var userProfile:Housemate? = null

    init{
        val housemateDao = UserDatabase.getDatabase(application).housemateDao()
        repository = HousemateRepository(housemateDao)
        readAllData = repository.readAllData
    }

    fun getUserProfile(userId:Int) : Housemate?{
        viewModelScope.launch(Dispatchers.IO) {
            userProfile = repository.getUserProfile(userId)
        }
        return userProfile
    }

    fun addProfile (housemate: Housemate){
        viewModelScope.launch (Dispatchers.IO){
            repository.addProfile(housemate)
        }
    }

    fun updateProfile (housemate: Housemate){
        viewModelScope.launch (Dispatchers.IO){
            repository.updateProfile(housemate)
        }
    }
}