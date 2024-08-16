package com.skymilk.weatherapp.di

import android.content.Context
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.skymilk.weatherapp.data.remote.FusedLocationProvider
import com.skymilk.weatherapp.data.remote.WeatherApi
import com.skymilk.weatherapp.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    private val json = Json {
        //입력 아이디가 일치되지 않아도 강제로 적용
        coerceInputValues = true
        ignoreUnknownKeys = true
    }

    @Provides
    @Singleton
    fun provideRetrofit(): WeatherApi {
        val contentType = "application/json".toMediaType()

        return Retrofit.Builder()
            .baseUrl(Constants.API_BASE_URL)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
            .create(WeatherApi::class.java)
    }

    @Provides
    @Singleton
    fun provideFusedLocationProvider(@ApplicationContext context: Context): FusedLocationProvider =
        FusedLocationProvider(context)
}