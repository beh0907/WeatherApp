package com.skymilk.weatherapp.presentation.home

import com.skymilk.weatherapp.domain.models.Daily
import com.skymilk.weatherapp.domain.models.Weather

data class HomeState(
    val weather: Weather? = null,
    val error:String? = null,
    val isLoading:Boolean = false,
    val dailyWeatherInfo: Daily.WeatherInfo? = null
)