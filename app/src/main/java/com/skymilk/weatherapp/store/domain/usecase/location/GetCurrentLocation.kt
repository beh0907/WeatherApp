package com.skymilk.weatherapp.store.domain.usecase.location

import android.location.Location
import com.skymilk.weatherapp.store.domain.repository.DataStoreRepository
import com.skymilk.weatherapp.store.domain.repository.LocationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCurrentLocation @Inject constructor(
    private val locationRepository: LocationRepository
) {

    operator fun invoke(): Flow<Location?> {
        return locationRepository.getCurrentLocation()
    }
}