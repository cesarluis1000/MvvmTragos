package com.example.mvvmtragos.data.datasource

import com.example.mvvmtragos.data.model.Drink
import com.example.mvvmtragos.data.model.DrinkEntity
import com.example.mvvmtragos.data.network.Resource

interface DrinkDataSource {

    suspend fun getDrinksByName(drinkName:String): Resource<List<Drink>>
    suspend fun getDrinksFavorites(): Resource<List<DrinkEntity>>
    suspend fun insertDrinkRoom(drinkEntity: DrinkEntity)
    suspend fun deleteDrink(drinkEntity: DrinkEntity)
}