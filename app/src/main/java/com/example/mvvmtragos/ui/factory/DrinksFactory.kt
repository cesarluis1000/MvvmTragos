package com.example.mvvmtragos.ui.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mvvmtragos.data.repository.DrinkRepository

class DrinksFactory(
    private val drinkRepository: DrinkRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(DrinkRepository::class.java).newInstance(drinkRepository)
    }
}