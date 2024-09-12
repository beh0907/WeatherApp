package com.skymilk.weatherapp.store.data.mapper

import com.skymilk.weatherapp.store.data.remote.models.ApiCurrentWeather
import com.skymilk.weatherapp.store.domain.models.CurrentWeather
import com.skymilk.weatherapp.store.domain.models.WeatherInfoItem
import com.skymilk.weatherapp.utils.DateUtil
import com.skymilk.weatherapp.utils.WeatherUtil


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
        return DateUtil.formatUnixDate("MMM,d", time)
    }

    private fun parseWeatherStatus(code: Int): WeatherInfoItem {
        return WeatherUtil.getWeatherInfo(code)
    }

    private fun parseWeatherDirection(direction: Double):String {
        return WeatherUtil.getWindDirection(direction)
    }

}