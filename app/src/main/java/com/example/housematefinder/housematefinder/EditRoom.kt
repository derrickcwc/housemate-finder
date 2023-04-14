package com.example.housematefinder.housematefinder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.housematefinder.R
import com.example.housematefinder.database.RoomList
import com.example.housematefinder.roomfinder.RoomFinderProfile
import com.example.housematefinder.roomfinder.RoomViewModel
import com.example.housematefinder.user.UserViewModel

class EditRoom : AppCompatActivity() {
    private lateinit var mRoomViewModel: RoomViewModel
    private val mUserViewModel = UserViewModel.instance
    private val user = mUserViewModel.getLoginStatus()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_room)

        mRoomViewModel = ViewModelProvider(this).get(RoomViewModel::class.java)

        val type = intent.getStringExtra("type")
        val address = intent.getStringExtra("address")
        val city = intent.getStringExtra("city")
        val price = intent.getIntExtra("price",0)
        val description = intent.getStringExtra("description")
        val femaleOnly = intent.getBooleanExtra("gender",false)
        val status = intent.getBooleanExtra("status",false)

        findViewById<EditText>(R.id.editType).setText(type)
        findViewById<EditText>(R.id.editAddress).setText(address)
        findViewById<EditText>(R.id.editCity).setText(city)
        if(price != 0){
            findViewById<EditText>(R.id.editPrice).setText(price.toString())
        }
        findViewById<EditText>(R.id.editDescription).setText(description)
        findViewById<Switch>(R.id.switchGender).isChecked = femaleOnly
        findViewById<Switch>(R.id.switchRoomStatus).isChecked = status

        val btnUpdate = findViewById<Button>(R.id.btnUpdateRoom)
        btnUpdate.setOnClickListener {
            updateRoom()
        }

    }

    private fun updateRoom() {
        val type = findViewById<EditText>(R.id.editType).text.toString()
        val city = findViewById<EditText>(R.id.editCity).text.toString()
        val address = findViewById<EditText>(R.id.editAddress).text.toString()
        val price = findViewById<EditText>(R.id.editPrice).text.toString()
        val description = findViewById<EditText>(R.id.editDescription).text.toString()
        val femaleOnly = findViewById<Switch>(R.id.switchGender).isChecked
        val status = findViewById<Switch>(R.id.switchRoomStatus).isChecked

        if(inputValidation(type, city, address, price, description)){
            val id = intent.getIntExtra("roomListId", 0)
            val roomList = RoomList(id, user!!.userId, address, city, femaleOnly, type, price.toInt(), description, status)

            //If current user don't have room, add new room, else update room
            if(id == 0){
                //Insert new data
                mRoomViewModel.addRoom(roomList)
                Toast.makeText(this,"Successfull Added!", Toast.LENGTH_LONG).show()
            }else{
                //Update data
                mRoomViewModel.updateRoom(roomList)
                Toast.makeText(this,"Successfull Updated!", Toast.LENGTH_LONG).show()
            }
            val intent = Intent(this, HousemateFinderProfile::class.java)
            startActivity(intent)
        }else{
            Toast.makeText(this,"Please insert all fields.", Toast.LENGTH_LONG).show()
        }
    }

    private fun inputValidation(type: String, city: String, address: String, price: String, description: String): Boolean {
        return!(TextUtils.isEmpty(type) || TextUtils.isEmpty(city) || TextUtils.isEmpty(address) ||
                TextUtils.isEmpty(price) || TextUtils.isEmpty(description))
    }
}