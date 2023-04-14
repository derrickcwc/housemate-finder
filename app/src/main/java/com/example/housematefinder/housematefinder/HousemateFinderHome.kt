package com.example.housematefinder.housematefinder

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CompoundButton
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.housematefinder.R
import com.example.housematefinder.database.Housemate
import com.example.housematefinder.database.UserDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class HousemateFinderHome : AppCompatActivity() {

    private lateinit var appDb: UserDatabase
    var housemate: Housemate? = null
    lateinit var housemateList : List<Housemate>
    lateinit var recyclerView:RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_housemate_finder_home)

        appDb = UserDatabase.getDatabase(this)

        recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val btnProfile = findViewById<Button>(R.id.btnhousematefinderProfile)
        btnProfile.setOnClickListener{
            val intent = Intent(this, HousemateFinderProfile::class.java)
            startActivity(intent)
        }
        val switchFemaleOnly = findViewById<Switch>(R.id.searchFemale)
        switchFemaleOnly.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            onStart()
        })
    }

    override fun onStart() {
        super.onStart()
        GlobalScope.launch {
            try{
                val switchFemaleOnly = findViewById<Switch>(R.id.searchFemale)
                if (switchFemaleOnly.isChecked){
                    housemateList = appDb.housemateDao().getFemaleOnly()
                    displayData(housemateList)
                }else{
                    housemateList = appDb.housemateDao().getHousemateList()
                    displayData(housemateList)
                }

            }catch(e: NullPointerException){
                e.printStackTrace()
            }
        }
    }

    private suspend fun displayData(housemateList: List<Housemate>) {
        withContext(Dispatchers.Main){
            display()
        }
    }

    private fun display(){
        var adapter = ListAdapter(housemateList)
        recyclerView.adapter = adapter
        adapter.onItemClick = {
            val intent = Intent(this, HousemateProfile::class.java)
            intent.putExtra("profile", it)
            startActivity(intent)
        }
    }
}