package com.skymilk.weatherapp.store.domain.repository

import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {

    suspend fun saveCurrentLocation(location: Pair<Double, Double>)

    fun getLocation(): Flow<Pair<Double, Double>>

}