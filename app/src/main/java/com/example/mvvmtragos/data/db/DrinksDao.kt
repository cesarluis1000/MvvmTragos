package com.example.mvvmtragos.data.db

import androidx.room.*
import com.example.mvvmtragos.data.model.DrinkEntity

@Dao
interface DrinksDao {

    @Query("SELECT * FROM tragosEntity")
    suspend fun getAllFavoriteDrink():List<DrinkEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(drinkEntity: DrinkEntity)

    @Delete
    suspend fun deleteDrink(drinkEntity: DrinkEntity)
}