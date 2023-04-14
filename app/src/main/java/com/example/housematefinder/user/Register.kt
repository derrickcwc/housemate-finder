package com.example.housematefinder.user

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.housematefinder.R
import com.example.housematefinder.database.*

class Register : AppCompatActivity() {
    lateinit var userType : TextView
    private lateinit var mUserViewModel: UserViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        userType = findViewById<TextView>(R.id.titleUserType)

        val intent = intent
        var user = intent.getStringExtra("userType")

        userType.setText(user)

        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        var btnRegister = findViewById<Button>(R.id.btnInsert)

        btnRegister.setOnClickListener{
            registerNewUser()
        }
    }

    private fun registerNewUser(){
        val fullName = findViewById<EditText>(R.id.editTextFullName).text.toString()
        val email = findViewById<EditText>(R.id.editTextEmail).text.toString()
        val username = findViewById<EditText>(R.id.editTextUsername).text.toString()
        val password = findViewById<EditText>(R.id.editTextPassword).text.toString()
        val phone = findViewById<EditText>(R.id.editTextPhone).text.toString()
        val userType = intent.getStringExtra("userType").toString()


        if(inputValidation(fullName,email, username , password, phone)){
            //Create user object
            val user = User(0, fullName, email, username,password,phone, userType)
            //Add data to database
            mUserViewModel.registerUser(user)

            Toast.makeText(this,"Successfull Added!", Toast.LENGTH_LONG).show()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }else{
            Toast.makeText(this,"Please insert all fields.", Toast.LENGTH_LONG).show()
        }
    }

    private fun inputValidation(fullName: String, email : String, username : String, password: String, phone : String) : Boolean{
        return !(TextUtils.isEmpty(fullName) || TextUtils.isEmpty(email) || TextUtils.isEmpty(username) ||
                TextUtils.isEmpty(password) || TextUtils.isEmpty(phone))
    }
}