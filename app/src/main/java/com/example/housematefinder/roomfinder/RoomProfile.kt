package com.example.housematefinder.roomfinder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.housematefinder.R
import com.example.housematefinder.database.Housemate
import com.example.housematefinder.database.RoomList
import com.example.housematefinder.database.User
import com.example.housematefinder.housematefinder.HousemateFinderHome
import com.example.housematefinder.housematefinder.HousemateFinderProfile
import com.example.housematefinder.user.UserViewModel

class RoomProfile : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room_profile)

        val profile = intent.getParcelableExtra<RoomList>("profile")

        val name = findViewById<TextView>(R.id.roomProfileFullName)
        val phone = findViewById<TextView>(R.id.roomProfilePhone)
        val houseType = findViewById<TextView>(R.id.roomProfileType)
        val address = findViewById<TextView>(R.id.roomProfileAddress)
        val city = findViewById<TextView>(R.id.roomProfileCity)
        val price = findViewById<TextView>(R.id.roomProfilePrice)
        val gender = findViewById<TextView>(R.id.roomProfileGender)
        val description = findViewById<TextView>(R.id.roomProfileDescription)

        val mUserViewModel = UserViewModel.instance
        val userList:List<User> = mUserViewModel.getUserList()

        if (profile != null) {
            for(user in userList){
                if(user.userId == profile.userId){
                    name.text = user.fullName
                    phone.text = user.phone
                }
            }
            houseType.text = profile.house_type
            address.text = profile.address
            city.text = profile.city
            price.text = profile.price.toString()
            description.text = profile.description
            if(profile.female_only){
                gender.text = "Female Only"
            }else{
                gender.text = "No Gender Restriction"
            }
        }

        val btnHome = findViewById<Button>(R.id.btnroomProfileHome)
        val btnProfile = findViewById<Button>(R.id.btnroomProfileProfile)

        btnHome.setOnClickListener{
            val intent = Intent(this, RoomFinderHome::class.java)
            startActivity(intent)
        }
        btnProfile.setOnClickListener{
            val intent = Intent(this, RoomFinderProfile::class.java)
            startActivity(intent)
        }
    }
}