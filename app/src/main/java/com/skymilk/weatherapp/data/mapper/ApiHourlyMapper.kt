package com.skymilk.weatherapp.data.mapper

import com.skymilk.weatherapp.data.remote.models.ApiHourlyWeather
import com.skymilk.weatherapp.domain.models.Hourly
import com.skymilk.weatherapp.utils.Util
import com.skymilk.weatherapp.utils.WeatherInfoItem

class ApiHourlyMapper : ApiMapper<Hourly, ApiHourlyWeather> {
    override fun mapToDomain(apiEntity: ApiHourlyWeather): Hourly {
        return Hourly(
            temperature = apiEntity.temperature2m,
            time = parseTime(apiEntity.time),
            weatherStatus = parseWeatherStatus(apiEntity.weatherCode),
        )
    }

    private fun parseTime(time: List<Long>): List<String> {
        return time.map { Util.formatUnixDate("HH:mm", it) }
    }

    private fun parseWeatherStatus(code: List<Int>):List<WeatherInfoItem> {
        return code.map { Util.getWeatherInfo(it) }
    }

    private fun parseWeatherDirection(direction: List<Double>):List<String> {
        return direction.map { Util.getWindDirection(it) }
    }

}