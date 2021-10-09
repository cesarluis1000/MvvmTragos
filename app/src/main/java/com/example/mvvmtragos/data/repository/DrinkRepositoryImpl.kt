package com.example.mvvmtragos.data.repository

import com.example.mvvmtragos.data.datasource.DrinkDataSource
import com.example.mvvmtragos.data.model.Drink
import com.example.mvvmtragos.data.model.DrinkEntity
import com.example.mvvmtragos.data.network.Resource
import javax.inject.Inject

class DrinkRepositoryImpl @Inject constructor(
    private val drinkDataSource: DrinkDataSource
): DrinkRepository {
    override suspend fun getDrinksByName(drinkName: String): Resource<List<Drink>> {
        return drinkDataSource.getDrinksByName(drinkName)
    }

    override suspend fun getDrinksFavorites(): Resource<List<DrinkEntity>> {
        return drinkDataSource.getDrinksFavorites()
    }

    override suspend fun insertDrink(drinkEntity: DrinkEntity) {
        drinkDataSource.insertDrinkRoom(drinkEntity)
    }

    override suspend fun deleteDrink(drinkEntity: DrinkEntity) {
        drinkDataSource.deleteDrink(drinkEntity)
    }
}