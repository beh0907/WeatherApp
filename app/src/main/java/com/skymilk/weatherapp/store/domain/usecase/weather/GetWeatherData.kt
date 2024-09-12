package com.skymilk.weatherapp.store.domain.usecase.weather

import com.skymilk.weatherapp.store.domain.models.Weather
import com.skymilk.weatherapp.store.domain.repository.DataStoreRepository
import com.skymilk.weatherapp.store.domain.repository.WeatherRepository
import com.skymilk.weatherapp.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetWeatherData @Inject constructor(
    private val weatherRepository: WeatherRepository
) {

    operator fun invoke(location: Pair<Double, Double>): Flow<Resource<Weather>> {
        return weatherRepository.getWeatherData(location)
    }
}