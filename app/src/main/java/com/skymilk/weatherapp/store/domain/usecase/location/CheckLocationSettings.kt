package com.skymilk.weatherapp.store.domain.usecase.location

import com.skymilk.weatherapp.store.domain.repository.LocationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CheckLocationSettings @Inject constructor(
    private val locationRepository: LocationRepository
) {

    operator fun invoke(onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        return locationRepository.checkLocationSettings(onSuccess, onFailure)
    }
}