package com.skymilk.weatherapp.di

import com.skymilk.weatherapp.store.domain.repository.DataStoreRepository
import com.skymilk.weatherapp.store.domain.repository.LocationRepository
import com.skymilk.weatherapp.store.domain.repository.WeatherRepository
import com.skymilk.weatherapp.store.domain.usecase.datastore.DataStoreUseCase
import com.skymilk.weatherapp.store.domain.usecase.datastore.GetLocation
import com.skymilk.weatherapp.store.domain.usecase.datastore.SaveCurrentLocation
import com.skymilk.weatherapp.store.domain.usecase.location.GetCurrentLocation
import com.skymilk.weatherapp.store.domain.usecase.location.GetIsGpsEnabled
import com.skymilk.weatherapp.store.domain.usecase.location.LocationUseCase
import com.skymilk.weatherapp.store.domain.usecase.location.StopTracking
import com.skymilk.weatherapp.store.domain.usecase.weather.GetWeatherData
import com.skymilk.weatherapp.store.domain.usecase.weather.WeatherUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideDataStoreUseCases(
        dataStoreRepository: DataStoreRepository
    ) = DataStoreUseCase(
        GetLocation(dataStoreRepository),
        SaveCurrentLocation(dataStoreRepository)
    )


    @Provides
    @Singleton
    fun provideLocationUseCases(
        locationRepository: LocationRepository
    ) = LocationUseCase(
        GetCurrentLocation(locationRepository),
        GetIsGpsEnabled(locationRepository),
        StopTracking(locationRepository)
    )


    @Provides
    @Singleton
    fun provideWeatherUseCases(
        weatherRepository: WeatherRepository
    ) = WeatherUseCase(
        GetWeatherData(weatherRepository)
    )
}