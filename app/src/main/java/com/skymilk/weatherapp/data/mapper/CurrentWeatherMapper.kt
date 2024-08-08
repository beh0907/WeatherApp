package com.skymilk.weatherapp.data.mapper

import com.skymilk.weatherapp.data.remote.models.ApiCurrentWeather
import com.skymilk.weatherapp.domain.models.CurrentWeather
import com.skymilk.weatherapp.utils.Util
import com.skymilk.weatherapp.utils.WeatherInfoItem

class CurrentWeatherMapper : ApiMapper<CurrentWeather, ApiCurrentWeather> {
    override fun mapToDomain(apiEntity: ApiCurrentWeather): CurrentWeather {
        return CurrentWeather(
            temperature = apiEntity.temperature2m,
            time = parseTime(apiEntity.time),
            weatherStatus = parseWeatherStatus(apiEntity.weatherCode),
            windDirection = parseWeatherDirection(apiEntity.windDirection10m),
            windSpeed = apiEntity.windSpeed10m,
            isDay = apiEntity.isDay == 1
        )
    }

    private fun parseTime(time: Long): String {
        return Util.formatUnixDate("MMM,d", time)
    }

    private fun parseWeatherStatus(code: Int):WeatherInfoItem {
        return Util.getWeatherInfo(code)
    }

    private fun parseWeatherDirection(direction: Double):String {
        return Util.getWindDirection(direction)
    }

}