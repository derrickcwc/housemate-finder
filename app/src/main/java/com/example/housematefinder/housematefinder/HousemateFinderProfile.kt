package com.example.housematefinder.housematefinder

import android.content.ClipDescription
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.housematefinder.R
import com.example.housematefinder.database.Housemate
import com.example.housematefinder.database.RoomList
import com.example.housematefinder.database.UserDatabase
import com.example.housematefinder.user.MainActivity
import com.example.housematefinder.user.UserViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.w3c.dom.Text
import java.lang.NullPointerException

class HousemateFinderProfile : AppCompatActivity() {

    private lateinit var appDb: UserDatabase
    var roomList:RoomList? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_housemate_finder_profile)

        appDb = UserDatabase.getDatabase(this)

        val btnHome = findViewById<Button>(R.id.btnhousematefinderHome3)
        val btnEditRoom = findViewById<Button>(R.id.btnEditRoom)
        val btnLogout = findViewById<Button>(R.id.btnLogout2)

        btnLogout.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        btnHome.setOnClickListener {
            val intent = Intent(this, HousemateFinderHome::class.java)
            startActivity(intent)
        }
        btnEditRoom.setOnClickListener {
            val intent = Intent(this, EditRoom::class.java)
            intent.putExtra("roomListId", roomList?.id)
            intent.putExtra("type" , roomList?.house_type)
            intent.putExtra("address" , roomList?.address)
            intent.putExtra("city" , roomList?.city)
            intent.putExtra("price" , roomList?.price)
            intent.putExtra("description" , roomList?.description)
            intent.putExtra("gender" , roomList?.female_only)
            intent.putExtra("status" , roomList?.status)
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        val mUserViewModel = UserViewModel.instance

        val name = findViewById<TextView>(R.id.roomFullname)
        val phone = findViewById<TextView>(R.id.roomPhone)

        val user = mUserViewModel.getLoginStatus()
        if(user != null){
            name.text = user.fullName
            phone.text = user.phone
        }
        GlobalScope.launch {
            if (user != null) {
                try{
                    roomList = appDb.roomListDao().getProfile(user.userId)!!
                    displayData(roomList)
                }catch(e: NullPointerException){
                    e.printStackTrace()
                }
            }
        }
    }
    private suspend fun displayData(roomList: RoomList?){
        withContext(Dispatchers.Main){
            val type = findViewById<TextView>(R.id.roomType)
            val address = findViewById<TextView>(R.id.roomAddress)
            val city = findViewById<TextView>(R.id.roomCity)
            val price = findViewById<TextView>(R.id.roomPrice)
            val gender = findViewById<TextView>(R.id.roomGender)
            val description = findViewById<TextView>(R.id.roomDescription)

            if(roomList != null){
                type.text = roomList.house_type
                address.text = roomList.address
                city.text = roomList.city
                price.text = roomList.price.toString()
                description.text = roomList.description
                if (roomList.female_only){
                    gender.text = "Female only"
                }else{
                    gender.text = "No restrictions"
                }
            }
        }
    }
}