package com.example.housematefinder.database

import android.content.Context
import androidx.room.*


@Database(entities = [User::class, RoomList::class, Housemate::class], version = 1, exportSchema = false)

abstract class UserDatabase : RoomDatabase(){
    abstract fun userDao() : UserDao
    abstract fun roomListDao() :RoomListDao
    abstract fun housemateDao():HousemateDao

    companion object{
        @Volatile
        private var INSTANCE:UserDatabase? = null
        fun getDatabase(context: Context): UserDatabase {
            synchronized(this){
                var instance = INSTANCE
                if(instance == null) {
                    instance =Room.databaseBuilder(
                        context.applicationContext,
                        UserDatabase::class.java,
                        "user_database")
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE =instance
                }
                return instance
            }
        }
    }
}
