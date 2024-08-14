package com.skymilk.weatherapp.domain.repository

import android.location.Location
import kotlinx.coroutines.flow.Flow

interface LocationRepository {

    fun getCurrentLocation(): Flow<Location?>

    fun isGpsEnabled(): Flow<Boolean>
}