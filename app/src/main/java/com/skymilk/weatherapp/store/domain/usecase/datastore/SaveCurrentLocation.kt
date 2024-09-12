package com.skymilk.weatherapp.store.domain.usecase.datastore

import com.skymilk.weatherapp.store.domain.repository.DataStoreRepository
import javax.inject.Inject

class SaveCurrentLocation @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) {

    suspend operator fun invoke(location: Pair<Double, Double>) {
        dataStoreRepository.saveCurrentLocation(location)
    }

}