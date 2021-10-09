package com.example.mvvmtragos.data.network

import com.example.mvvmtragos.data.model.Drinks
import retrofit2.http.GET
import retrofit2.http.Query

interface WebService {

    @GET("search.php")
    suspend fun getDrinkByName(@Query("s") tragoName: String): Drinks
}
