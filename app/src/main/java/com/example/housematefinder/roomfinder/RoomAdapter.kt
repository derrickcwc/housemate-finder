package com.example.housematefinder.roomfinder

import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.housematefinder.R
import com.example.housematefinder.database.Housemate
import com.example.housematefinder.database.RoomList
import com.example.housematefinder.housematefinder.ListAdapter

class RoomAdapter(private var items: List<RoomList>) :RecyclerView.Adapter<RoomAdapter.MyViewHolder>(){

    var onItemClick : ((RoomList) -> Unit)? = null

    fun setListData( data: List<RoomList>){
        this.items = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomAdapter.MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.custom_room_row,parent,false))
    }

    override fun onBindViewHolder(holder: RoomAdapter.MyViewHolder, position: Int) {
        val roomList = items[position]
        holder.bind(roomList)
        holder.itemView.setOnClickListener{
            onItemClick?.invoke(roomList)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class MyViewHolder(view: View): RecyclerView.ViewHolder(view){
        val city = view.findViewById<TextView>(R.id.roomFinderHomeCity)
        val price = view.findViewById<TextView>(R.id.roomFinderHomePrice)
        val gender = view.findViewById<TextView>(R.id.roomFinderHomeGender)
        val houseType = view.findViewById<ImageView>(R.id.imageHouseType)


        fun bind(data: RoomList){
            city.text = data.city
            price.text = data.price.toString()
            if(data.female_only){
                gender.text = "Female Only"
            }else{
                gender.text = "No Gender Restriction"
            }

            if(data.house_type == "Condominium"){
                houseType.setImageResource(R.mipmap.condominium)
            } else if(data.house_type == "House"){
                houseType.setImageResource(R.mipmap.home)
            }

        }
    }
}