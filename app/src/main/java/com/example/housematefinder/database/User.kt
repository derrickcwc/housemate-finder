package com.example.housematefinder.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class User(
    @PrimaryKey(autoGenerate = true)
    var userId: Int,

    @ColumnInfo(name = "full_name")
    var fullName : String?,

    @ColumnInfo(name = "email")
    var email: String?,

    @ColumnInfo(name = "username")
    var username : String?,

    @ColumnInfo(name = "password")
    var password : String?,

    @ColumnInfo(name = "phone")
    var phone: String?,

    @ColumnInfo(name = "usertype")
    var userType : String?,
)