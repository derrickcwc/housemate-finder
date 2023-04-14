package com.example.housematefinder.housematefinder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.example.housematefinder.R
import com.example.housematefinder.database.Housemate
import com.example.housematefinder.database.User
import com.example.housematefinder.user.UserViewModel

class HousemateProfile : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_housemate_profile)

        val profile = intent.getParcelableExtra<Housemate>("profile")

        val name = findViewById<TextView>(R.id.housemateFullName)
        val phone = findViewById<TextView>(R.id.housematePhone)
        val age = findViewById<TextView>(R.id.housemateAge)
        val city = findViewById<TextView>(R.id.housemateCity)
        val gender = findViewById<TextView>(R.id.housemateGender)
        val description = findViewById<TextView>(R.id.housemateDescription)
        val avatar = findViewById<ImageView>(R.id.imageGender)

        val mUserViewModel = UserViewModel.instance
        val userList:List<User> = mUserViewModel.getUserList()

        if (profile != null) {
            for(user in userList){
                if(user.userId == profile.userId){
                    name.text = user.fullName
                    phone.text = user.phone
                }
            }
            age.text = profile.age.toString()
            city.text = profile.city
            gender.text = profile.gender
            description.text = profile.description
            if(profile.gender == "Male"){
                avatar.setImageResource(R.mipmap.maleavatar)
            }else if(profile.gender == "Female"){
                avatar.setImageResource(R.mipmap.femaleavatar)
            }

        }

        val btnHome = findViewById<Button>(R.id.btnhousematefinderHome2)
        val btnProfile = findViewById<Button>(R.id.btnhousematefinderProfile2)

        btnHome.setOnClickListener{
            val intent = Intent(this, HousemateFinderHome::class.java)
            startActivity(intent)
        }
        btnProfile.setOnClickListener{
            val intent = Intent(this, HousemateFinderProfile::class.java)
            startActivity(intent)
        }
    }
}