package com.example.housematefinder.user

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.example.housematefinder.R


class RegisterType : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_type)

        val btnRoomFinder = findViewById<ImageButton>(R.id.btnRegisterRoomFinder)
        val btnHousemateFinder = findViewById<ImageButton>(R.id.btnRegisterHousemateFinder)

        btnRoomFinder.setOnClickListener{
            var intent = Intent(this, Register::class.java)
            intent.putExtra("userType", "RoomFinder")
            startActivity(intent)
        }

        btnHousemateFinder.setOnClickListener{
            var intent = Intent(this, Register::class.java)
            intent.putExtra("userType", "HousemateFinder")
            startActivity(intent)
        }
    }



}