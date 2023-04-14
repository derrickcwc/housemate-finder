package com.example.housematefinder.user

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.housematefinder.R
import com.example.housematefinder.housematefinder.HousemateFinderHome
import com.example.housematefinder.roomfinder.RoomFinderHome

class MainActivity : AppCompatActivity() {
    lateinit var mUserViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)


        val btnRegister = findViewById<Button>(R.id.btnRegister)
        btnRegister.setOnClickListener {
            val intent = Intent(this, RegisterType::class.java)
            startActivity(intent)
        }

    }

    override fun onStart() {
        super.onStart()

        val btnLogin = findViewById<Button>(R.id.btnLogin)
        btnLogin.setOnClickListener{
            login()
            mUserViewModel.getAllUser()
        }
    }
    private fun login() {
        val user = findViewById<EditText>(R.id.txtUsername).text.toString()
        val pass = findViewById<EditText>(R.id.txtPassword).text.toString()


        if(user.isEmpty() || pass.isEmpty()){
            Toast.makeText(this,"Please insert login details", Toast.LENGTH_LONG).show()
        }else{
            mUserViewModel.loginUser(user,pass)
            val loginStatus = mUserViewModel.getLoginStatus()
//            val loginStatus = mUserViewModel.getLogin(user,pass)

            if(loginStatus == null) {
                Toast.makeText(this,"Invalid Login Credentials!", Toast.LENGTH_LONG).show()
            }else{
                if(loginStatus.userType == "RoomFinder"){
                    Toast.makeText(this,"Login Successful!", Toast.LENGTH_LONG).show()
                    val intent = Intent(this, RoomFinderHome::class.java)
                    startActivity(intent)
                }else if(loginStatus.userType == "HousemateFinder"){
                    Toast.makeText(this,"Login Successful!", Toast.LENGTH_LONG).show()
                    val intent = Intent(this, HousemateFinderHome::class.java)
                    startActivity(intent)
                }
            }
        }

    }
}