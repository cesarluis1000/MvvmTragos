package com.example.mvvmtragos.data.datasource

import com.example.mvvmtragos.data.db.AppDatabase
import com.example.mvvmtragos.data.db.DrinksDao
import com.example.mvvmtragos.data.model.Drink
import com.example.mvvmtragos.data.model.DrinkEntity
import com.example.mvvmtragos.data.network.Resource
import com.example.mvvmtragos.data.network.RetrofitClient
import com.example.mvvmtragos.data.network.WebService
import javax.inject.Inject

class DrinkDataSourceImpl @Inject constructor(
    private val drinksDao: DrinksDao,
    private val webService: WebService
): DrinkDataSource {

    override suspend fun getDrinksByName(drinkName: String): Resource<List<Drink>> {
        return Resource.Success(webService.getDrinkByName(drinkName).drinks)
    }

    override suspend fun getDrinksFavorites(): Resource<List<DrinkEntity>> {
        return Resource.Success(drinksDao.getAllFavoriteDrink())
    }

    override suspend fun insertDrinkRoom(drinkEntity: DrinkEntity) {
        drinksDao.insertFavorite(drinkEntity)
    }

    override suspend fun deleteDrink(drinkEntity: DrinkEntity) {
        drinksDao.deleteDrink(drinkEntity)
    }
}