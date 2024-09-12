package com.skymilk.weatherapp.store.domain.repository

import com.skymilk.weatherapp.store.domain.models.Weather
import com.skymilk.weatherapp.utils.Resource
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {

    fun getWeatherData(location: Pair<Double, Double>): Flow<Resource<Weather>>
}