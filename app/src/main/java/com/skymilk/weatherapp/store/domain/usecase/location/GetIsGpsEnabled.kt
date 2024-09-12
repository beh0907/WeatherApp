package com.skymilk.weatherapp.store.domain.usecase.location

import com.skymilk.weatherapp.store.domain.repository.DataStoreRepository
import com.skymilk.weatherapp.store.domain.repository.LocationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetIsGpsEnabled @Inject constructor(
    private val locationRepository: LocationRepository
) {

    operator fun invoke(): Flow<Boolean> {
        return locationRepository.getIsGpsEnabled()
    }
}