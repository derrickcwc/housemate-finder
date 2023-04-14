package com.example.housematefinder.user

import androidx.lifecycle.LiveData
import com.example.housematefinder.database.User
import com.example.housematefinder.database.UserDao

class UserRepository (private val userDao:UserDao){

    var loginStatus:User? = null

    suspend fun addUser(user:User){
        userDao.insert(user)
    }

    suspend fun login(username:String,password:String){
        loginStatus = userDao.login(username,password)
    }

    suspend fun readAllData() : List<User>{
        return userDao.readAllData()
    }
}