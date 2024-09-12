package com.skymilk.weatherapp.store.domain.repository

import android.location.Location
import kotlinx.coroutines.flow.Flow

interface LocationRepository {

    fun getCurrentLocation(): Flow<Location?>

    fun getIsGpsEnabled(): Flow<Boolean>

    suspend fun stopTracking()
}