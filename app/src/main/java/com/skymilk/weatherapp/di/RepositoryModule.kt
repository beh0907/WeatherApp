package com.skymilk.weatherapp.di

import com.skymilk.weatherapp.data.repository.DataStoreStoreRepositoryImpl
import com.skymilk.weatherapp.data.repository.LocationRepositoryImpl
import com.skymilk.weatherapp.data.repository.WeatherRepositoryImpl
import com.skymilk.weatherapp.domain.repository.DataStoreRepository
import com.skymilk.weatherapp.domain.repository.LocationRepository
import com.skymilk.weatherapp.domain.repository.WeatherRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindWeatherRepository(weatherRepositoryImpl: WeatherRepositoryImpl): WeatherRepository

    @Binds
    @Singleton
    abstract fun bindDataStoreRepository(dataStoreStoreRepositoryImpl: DataStoreStoreRepositoryImpl): DataStoreRepository

    @Binds
    @Singleton
    abstract fun bindLocationRepository(locationRepositoryImpl: LocationRepositoryImpl): LocationRepository
}