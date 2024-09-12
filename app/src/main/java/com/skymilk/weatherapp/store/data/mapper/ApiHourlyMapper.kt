package com.skymilk.weatherapp.store.data.mapper

import com.skymilk.weatherapp.store.data.remote.models.ApiHourlyWeather
import com.skymilk.weatherapp.store.domain.models.Hourly
import com.skymilk.weatherapp.store.domain.models.WeatherInfoItem
import com.skymilk.weatherapp.utils.DateUtil
import com.skymilk.weatherapp.utils.WeatherUtil


class ApiHourlyMapper : ApiMapper<Hourly, ApiHourlyWeather> {
    override fun mapToDomain(apiEntity: ApiHourlyWeather): Hourly {
        return Hourly(
            temperature = apiEntity.temperature2m,
            time = parseTime(apiEntity.time),
            weatherStatus = parseWeatherStatus(apiEntity.weatherCode),
        )
    }

    private fun parseTime(time: List<Long>): List<String> {
        return time.map { DateUtil.formatUnixDate("a h시", it) }
    }

    private fun parseWeatherStatus(code: List<Int>): List<WeatherInfoItem> {
        return code.map { WeatherUtil.getWeatherInfo(it) }
    }

    private fun parseWeatherDirection(direction: List<Double>): List<String> {
        return direction.map { WeatherUtil.getWindDirection(it) }
    }

}