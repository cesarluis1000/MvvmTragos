package com.example.mvvmtragos.data.repository

import com.example.mvvmtragos.data.model.Drink
import com.example.mvvmtragos.data.model.DrinkEntity
import com.example.mvvmtragos.data.network.Resource

interface DrinkRepository {

    suspend fun getDrinksByName(drinkName: String): Resource<List<Drink>>

    suspend fun getDrinksFavorites(): Resource<List<DrinkEntity>>
    suspend fun insertDrink(drinkEntity: DrinkEntity)
    suspend fun deleteDrink(drinkEntity: DrinkEntity)
}