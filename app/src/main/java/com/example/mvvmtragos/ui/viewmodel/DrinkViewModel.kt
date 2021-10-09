package com.example.mvvmtragos.ui.viewmodel

import androidx.lifecycle.*
import com.example.mvvmtragos.data.model.Drink
import com.example.mvvmtragos.data.model.DrinkEntity
import com.example.mvvmtragos.data.network.Resource
import com.example.mvvmtragos.data.repository.DrinkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DrinkViewModel @Inject constructor(
    private val drinkRepository: DrinkRepository
):ViewModel() {

    private val drinkData = MutableLiveData<String>()

    fun setDrink(drinkName: String){
        drinkData.value = drinkName
    }

    init {
        setDrink("margarita")
    }

    val fetchDrinksList = drinkData.distinctUntilChanged().switchMap { drinkName ->
        liveData(Dispatchers.IO) {
            emit(Resource.Loading)
            try {
                emit(drinkRepository.getDrinksByName(drinkName))
            }catch (e: Exception){
                emit(Resource.Failure(e))
            }
        }
    }

    fun saveDrink(drinkEntity: DrinkEntity) {
        viewModelScope.launch {
            drinkRepository.insertDrink(drinkEntity)
        }
    }

    fun getTragosFavoritos() = liveData(Dispatchers.IO) {
        emit(Resource.Loading)
        try {
            emit(drinkRepository.getDrinksFavorites())
        }catch (e: Exception){
            emit(Resource.Failure(e))
        }
    }

    fun deleteDrink(drinkEntity: DrinkEntity) {
        viewModelScope.launch {
            drinkRepository.deleteDrink(drinkEntity)
        }
    }
}