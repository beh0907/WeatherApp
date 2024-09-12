package com.skymilk.weatherapp.store.data.mapper

import com.skymilk.weatherapp.store.data.remote.models.ApiDailyWeather
import com.skymilk.weatherapp.store.domain.models.Daily
import com.skymilk.weatherapp.store.domain.models.WeatherInfoItem
import com.skymilk.weatherapp.utils.DateUtil
import com.skymilk.weatherapp.utils.WeatherUtil

class ApiDailyMapper : ApiMapper<Daily, ApiDailyWeather> {
    override fun mapToDomain(apiEntity: ApiDailyWeather): Daily {
        return Daily(
            temperatureMax = apiEntity.temperature2mMax,
            temperatureMin = apiEntity.temperature2mMin,
            time = parseTime(apiEntity.time),
            weatherStatus = parseWeatherStatus(apiEntity.weatherCode),
            windDirection = parseWeatherDirection(apiEntity.windDirection10mDominant),
            windSpeed = apiEntity.windSpeed10mMax,
            sunrise = apiEntity.sunrise.map { DateUtil.formatUnixDate("HH:mm", it.toLong()) },
            sunset = apiEntity.sunset.map { DateUtil.formatUnixDate("HH:mm", it.toLong()) },
            uvIndex = apiEntity.uvIndexMax,
        )
    }

    private fun parseTime(time: List<Long>): List<String> {
        return time.map { DateUtil.formatUnixDate("E", it) }
    }

    private fun parseWeatherStatus(code: List<Int>):List<WeatherInfoItem> {
        return code.map { WeatherUtil.getWeatherInfo(it) }
    }

    private fun parseWeatherDirection(direction: List<Double>):List<String> {
        return direction.map { WeatherUtil.getWindDirection(it) }
    }

}