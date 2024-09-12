package com.skymilk.weatherapp.store.domain.usecase.datastore

data class DataStoreUseCase(
    val getLocation: GetLocation,
    val saveCurrentLocation: SaveCurrentLocation
)
