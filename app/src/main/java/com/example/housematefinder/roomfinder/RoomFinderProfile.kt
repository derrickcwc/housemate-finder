package com.example.housematefinder.roomfinder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.housematefinder.R
import com.example.housematefinder.database.Housemate
import com.example.housematefinder.database.UserDatabase
import com.example.housematefinder.housematefinder.HousemateViewModel
import com.example.housematefinder.user.MainActivity
import com.example.housematefinder.user.UserViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.NullPointerException

class RoomFinderProfile : AppCompatActivity() {
    private lateinit var userFullname : TextView
    private lateinit var userPhone : TextView
    private lateinit var userAge : TextView
    private lateinit var userCity : TextView
    private lateinit var userGender : TextView
    private lateinit var userDescription : TextView

    private lateinit var appDb:UserDatabase
    var housemate: Housemate? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room_finder_profile)

        appDb = UserDatabase.getDatabase(this)

        val btnEditProfile = findViewById<Button>(R.id.btnEditProfile)
        btnEditProfile.setOnClickListener{
            val intent = Intent(this, EditProfile::class.java)
            intent.putExtra("profileID", housemate?.id)
            intent.putExtra("age" , housemate?.age)
            intent.putExtra("city", housemate?.city)
            intent.putExtra("gender", housemate?.gender)
            intent.putExtra("description", housemate?.description)
            intent.putExtra("status", housemate?.status )
            startActivity(intent)
        }

        val btnHome = findViewById<Button>(R.id.btnroomfinderHome)
        btnHome.setOnClickListener{
            val intent = Intent(this, RoomFinderHome::class.java)
            startActivity(intent)
        }

        val btnLogout = findViewById<Button>(R.id.btnLogout)
        btnLogout.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        val mUserViewModel = UserViewModel.instance

        userFullname = findViewById(R.id.textFullName)
        userPhone = findViewById(R.id.textPhone)
        val user = mUserViewModel.getLoginStatus()

        if (user != null) {
            userFullname.setText(user.fullName)
            userPhone.setText(user.phone)
        }

        GlobalScope.launch {
            if (user != null) {
                try{
                    housemate = appDb.housemateDao().getProfile(user.userId)!!
                    displayData(housemate)
                }catch(e:NullPointerException){
                    e.printStackTrace()
                }
            }
        }
    }
    private suspend fun displayData(profile:Housemate?){
        withContext(Dispatchers.Main){
            userAge = findViewById(R.id.textAge)
            userCity = findViewById(R.id.textCity)
            userGender = findViewById(R.id.textGender)
            userDescription = findViewById(R.id.textDescription)

            if(profile != null){
                userAge.setText(profile.age.toString())
                userCity.setText(profile.city)
                userGender.setText(profile.gender)
                userDescription.setText(profile.description)
            }


        }
    }

}