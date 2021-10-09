package com.example.mvvmtragos.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mvvmtragos.data.model.DrinkEntity

@Database(entities = arrayOf(DrinkEntity::class), version = 1)
abstract class AppDatabase: RoomDatabase() {

    abstract fun drinksDao(): DrinksDao
    /*
    companion object{

        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase{
            INSTANCE = INSTANCE?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "tragosEntity").build()
            return INSTANCE!!
        }

        fun destroyInstance(){
            INSTANCE = null
        }
    }
    */
}