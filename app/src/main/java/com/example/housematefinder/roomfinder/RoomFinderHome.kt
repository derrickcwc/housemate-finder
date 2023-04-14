package com.example.housematefinder.roomfinder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CompoundButton
import android.widget.Switch
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.housematefinder.R
import com.example.housematefinder.database.Housemate
import com.example.housematefinder.database.RoomList
import com.example.housematefinder.database.UserDatabase
import com.example.housematefinder.housematefinder.HousemateProfile
import com.example.housematefinder.housematefinder.ListAdapter
import com.example.housematefinder.user.RegisterType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.NullPointerException

class RoomFinderHome : AppCompatActivity() {

    private lateinit var appDb: UserDatabase
    var roomList : RoomList? = null
    lateinit var allRoomList : List<RoomList>
    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room_finder_home)

        appDb = UserDatabase.getDatabase(this)

        recyclerView = findViewById<RecyclerView>(R.id.roomRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val btnProfile = findViewById<Button>(R.id.btnroomfinderProfile)
        btnProfile.setOnClickListener{
            val intent = Intent(this, RoomFinderProfile::class.java)
            startActivity(intent)
        }
        val switchFemaleOnly = findViewById<Switch>(R.id.roomFemale)
        switchFemaleOnly.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            onStart()
        })
    }
    override fun onStart() {
        super.onStart()
        GlobalScope.launch {
            try{
                val switchFemaleOnly = findViewById<Switch>(R.id.roomFemale)
                if (switchFemaleOnly.isChecked){
                    allRoomList = appDb.roomListDao().getFemaleOnly()
                    displayData(allRoomList)
                }else{
                    allRoomList = appDb.roomListDao().getRoomList()
                    displayData(allRoomList)
                }
            }catch(e: NullPointerException){
                e.printStackTrace()
            }
        }
    }

    private suspend fun displayData(allRoomList: List<RoomList>) {
        withContext(Dispatchers.Main){
            display()
        }
    }

    private fun display(){
        var adapter = RoomAdapter(allRoomList)
        recyclerView.adapter = adapter
        adapter.onItemClick = {
            val intent = Intent(this, RoomProfile::class.java)
            intent.putExtra("profile",it)
            startActivity(intent)
        }
    }
}