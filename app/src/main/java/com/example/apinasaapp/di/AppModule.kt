package com.example.apinasaapp.di

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.apinasaapp.NasaApiApp
import com.example.apinasaapp.api.NasaApiService
import com.example.apinasaapp.api.adapters.DateAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun provideNasaApiService(): NasaApiService{
        val moshi = Moshi.Builder()
            .add(DateAdapter())
            .add(KotlinJsonAdapterFactory())
            .build()

        return Retrofit.Builder()
            .baseUrl("https://images-api.nasa.gov/")
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(NasaApiService::class.java)
    }

}