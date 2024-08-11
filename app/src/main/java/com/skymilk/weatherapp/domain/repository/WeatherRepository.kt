package com.skymilk.weatherapp.domain.repository

import com.skymilk.weatherapp.domain.models.Weather
import com.skymilk.weatherapp.utils.Response
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {

    fun getWeatherData(location: Pair<Double, Double>): Flow<Response<Weather>>
}