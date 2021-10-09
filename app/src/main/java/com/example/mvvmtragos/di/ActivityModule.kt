package com.example.mvvmtragos.di

import com.example.mvvmtragos.data.datasource.DrinkDataSource
import com.example.mvvmtragos.data.datasource.DrinkDataSourceImpl
import com.example.mvvmtragos.data.repository.DrinkRepository
import com.example.mvvmtragos.data.repository.DrinkRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@InstallIn(ActivityRetainedComponent::class)
@Module
abstract class ActivityModule {

    @Binds
    abstract fun bindsDrinkRepository(
        drinkRepositoryImpl: DrinkRepositoryImpl
    ): DrinkRepository

    @Binds
    abstract fun bindsDrinkDataSource(
        drinkDataSourceImpl: DrinkDataSourceImpl
    ): DrinkDataSource
}