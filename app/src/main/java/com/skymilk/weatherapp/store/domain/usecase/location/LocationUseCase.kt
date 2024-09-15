package com.skymilk.weatherapp.store.domain.usecase.location

data class LocationUseCase(
    val getCurrentLocation: GetCurrentLocation,
    val getIsGpsEnabled: GetIsGpsEnabled,
    val stopTracking: StopTracking,
    val checkLocationSettings: CheckLocationSettings
)
