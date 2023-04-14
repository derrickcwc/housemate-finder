package com.example.housematefinder.roomfinder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.example.housematefinder.R
import com.example.housematefinder.database.Housemate
import com.example.housematefinder.database.User
import com.example.housematefinder.housematefinder.HousemateViewModel
import com.example.housematefinder.user.MainActivity
import com.example.housematefinder.user.UserViewModel

class EditProfile : AppCompatActivity() {
    private lateinit var mHousemateViewModel: HousemateViewModel
    private val mUserViewModel = UserViewModel.instance
    private val user = mUserViewModel.getLoginStatus()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        mHousemateViewModel = ViewModelProvider(this).get(HousemateViewModel::class.java)

        val passAge = intent.getIntExtra("age",0)
        val passCity = intent.getStringExtra("city")
        val passGender = intent.getStringExtra("gender")
        val passDescription = intent.getStringExtra("description")
        val passStatus = intent.getBooleanExtra("status",false)

        if (passAge != 0){
            findViewById<EditText>(R.id.txtAge).setText(passAge.toString())
        }
        findViewById<EditText>(R.id.txtCity).setText(passCity)
        findViewById<EditText>(R.id.txtGender).setText(passGender)
        findViewById<EditText>(R.id.txtDescription).setText(passDescription)
        findViewById<Switch>(R.id.switchStatus).isChecked = passStatus

        val btnUpdate = findViewById<Button>(R.id.btnUpdate)
        btnUpdate.setOnClickListener {
            updateUser()
        }

    }

    private fun updateUser(){
        val city = findViewById<EditText>(R.id.txtCity).text.toString()
        val age = findViewById<EditText>(R.id.txtAge).text.toString()
        val gender = findViewById<EditText>(R.id.txtGender).text.toString()
        val description = findViewById<EditText>(R.id.txtDescription).text.toString()
        val status = findViewById<Switch>(R.id.switchStatus).isChecked

        if(inputValidation(city, age, gender, description)){
            //Create housemate object
            val id = intent.getIntExtra("profileID", 0)
            val housemate = Housemate(id, user!!.userId, city, age.toInt(), gender, description,status)

            //If current user don't have profile, add new profile, else update profile
            if(id == 0){
                //Insert new data
                mHousemateViewModel.addProfile(housemate)
                Toast.makeText(this,"Successfull Added!", Toast.LENGTH_LONG).show()
            }else{
                //Update data
                mHousemateViewModel.updateProfile(housemate)
                Toast.makeText(this,"Successfull Updated!", Toast.LENGTH_LONG).show()
            }

            val intent = Intent(this, RoomFinderProfile::class.java)
            startActivity(intent)
        }else{
            Toast.makeText(this,"Please insert all fields.", Toast.LENGTH_LONG).show()
        }
    }

    private fun inputValidation(city: String, age : String, gender : String, description: String) : Boolean{
        return !(TextUtils.isEmpty(city) || TextUtils.isEmpty(age) || TextUtils.isEmpty(gender) ||
                TextUtils.isEmpty(description))
    }
}