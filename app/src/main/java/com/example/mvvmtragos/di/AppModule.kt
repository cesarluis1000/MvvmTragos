package com.example.mvvmtragos.di

import android.content.Context
import androidx.room.Room
import com.example.mvvmtragos.BuildConfig
import com.example.mvvmtragos.data.db.AppDatabase
import com.example.mvvmtragos.data.db.DrinksDao
import com.example.mvvmtragos.data.network.WebService
import com.example.mvvmtragos.utils.Constants
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providesDrinksDao(
        appDatabase: AppDatabase
    ) : DrinksDao {
        return appDatabase.drinksDao()
    }

    @Singleton
    @Provides
    fun providesAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "tragosEntity"
        ).build()
    }

    @Singleton
    @Provides
    fun providesWebService(
        retrofit:Retrofit
    ): WebService{
        return retrofit.create(WebService::class.java)
    }

    @Singleton
    @Provides
    fun providesRetrofit() : Retrofit {
        val requestInterceptor = Interceptor { chain ->
            val url = chain.request()
                .url
                .newBuilder()
                //.addQueryParameter("api_key", Constants.API_KEY)
                //.addQueryParameter("language", Constants.LANGUAGE)
                .build()

            val request = chain.request()
                .newBuilder()
                .url(url)
                .build()
            return@Interceptor chain.proceed(request)
        }

        val loggingInterceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        } else {
            loggingInterceptor.level = HttpLoggingInterceptor.Level.NONE
        }

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(requestInterceptor)
            .addInterceptor(loggingInterceptor)
            .connectTimeout(60, TimeUnit.SECONDS)
            .build()

        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .client(okHttpClient)
            .build()
    }

}