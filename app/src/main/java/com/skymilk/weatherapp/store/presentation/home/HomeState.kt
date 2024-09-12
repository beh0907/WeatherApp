package com.skymilk.weatherapp.store.presentation.home

import com.skymilk.weatherapp.store.domain.models.Daily
import com.skymilk.weatherapp.store.domain.models.Weather

data class HomeState(
    val weather: Weather? = null,
    val error: String? = null,
    val isLoading: Boolean = false,
    val dailyWeatherInfo: Daily.WeatherInfo? = null
)