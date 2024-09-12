package com.skymilk.weatherapp.store.domain.models

data class Weather(
    val currentWeather: CurrentWeather,
    val daily: Daily,
    val hourly: Hourly
)
