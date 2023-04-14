package com.example.housematefinder.housematefinder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.housematefinder.R
import com.example.housematefinder.database.Housemate
import com.example.housematefinder.database.User
import com.example.housematefinder.user.UserViewModel

class ListAdapter(private var items: List<Housemate>): RecyclerView.Adapter<ListAdapter.MyViewHolder>() {

    var onItemClick : ((Housemate) -> Unit)? = null

    fun setListData( data: List<Housemate>){
        this.items = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.custom_row,parent,false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val housemate = items[position]
        holder.bind(housemate)
        holder.itemView.setOnClickListener{
            onItemClick?.invoke(housemate)
        }

    }

    class MyViewHolder(view: View): RecyclerView.ViewHolder(view) {

        val name = view.findViewById<TextView>(R.id.homeName)
        val age = view.findViewById<TextView>(R.id.homeAge)
        val city = view.findViewById<TextView>(R.id.homeCity)
        val gender = view.findViewById<ImageView>(R.id.imgGender)

        val mUserViewModel = UserViewModel.instance
        val userList:List<User> = mUserViewModel.getUserList()

        fun bind(data: Housemate){
            val uid = data.userId
            for (user in userList){
                if(uid == user.userId){
                    name.text = user.fullName
                    break
                }
            }
            age.text = data.age.toString()
            city.text = data.city
            if(data.gender == "Female"){
                gender.setImageResource(R.mipmap.femaleavatar)
            } else if(data.gender == "Male"){
                gender.setImageResource(R.mipmap.maleavatar)
            }

        }
    }

}