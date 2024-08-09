package com.skymilk.weatherapp.data.remote.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiHourlyWeather(
    @SerialName("temperature_2m")
    val temperature2m: List<Double>,
    @SerialName("time")
    val time: List<Long>,
    @SerialName("weather_code")
    val weatherCode: List<Int>
)