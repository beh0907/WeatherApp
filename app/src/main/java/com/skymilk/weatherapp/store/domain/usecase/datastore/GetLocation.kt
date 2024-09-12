package com.skymilk.weatherapp.store.domain.usecase.datastore

import com.skymilk.weatherapp.store.domain.repository.DataStoreRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLocation @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) {

    operator fun invoke(): Flow<Pair<Double, Double>> {
        return dataStoreRepository.getLocation()
    }
}