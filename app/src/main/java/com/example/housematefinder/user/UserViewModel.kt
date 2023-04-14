package com.example.housematefinder.user

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.housematefinder.database.User
import com.example.housematefinder.database.UserDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserViewModel(application: Application) : AndroidViewModel(application) {

    lateinit var readAllData: List<User>
    private val repository: UserRepository

    companion object{
        lateinit var instance:UserViewModel
    }

    init{
        val userDao = UserDatabase.getDatabase(application).userDao()
        repository = UserRepository(userDao)
        instance = this
    }

//    fullName: String, email: String, username: String,password: String,phone: String, userType:String
//    User(0, fullName, email, username,password,phone, userType)
    fun registerUser(user:User) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addUser(user)
        }
    }

    fun loginUser(username: String,password: String){
        viewModelScope.launch {
            repository.login(username,password)
        }
    }
    fun getLoginStatus():User?{
        return repository.loginStatus
    }

    fun getAllUser(){
        viewModelScope.launch {
            readAllData = repository.readAllData()
        }
    }

    fun getUserList(): List<User>{
        return readAllData
    }

}