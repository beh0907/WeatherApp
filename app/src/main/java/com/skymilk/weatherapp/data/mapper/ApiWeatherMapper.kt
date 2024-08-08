package com.skymilk.weatherapp.data.mapper

import com.skymilk.weatherapp.data.remote.models.ApiCurrentWeather
import com.skymilk.weatherapp.data.remote.models.ApiDailyWeather
import com.skymilk.weatherapp.data.remote.models.ApiHourlyWeather
import com.skymilk.weatherapp.data.remote.models.ApiWeather
import com.skymilk.weatherapp.di.ApiCurrentWeatherMapperAnnotation
import com.skymilk.weatherapp.di.ApiDailyMapperAnnotation
import com.skymilk.weatherapp.di.ApiHourlyMapperAnnotation
import com.skymilk.weatherapp.domain.models.CurrentWeather
import com.skymilk.weatherapp.domain.models.Daily
import com.skymilk.weatherapp.domain.models.Hourly
import com.skymilk.weatherapp.domain.models.Weather
import javax.inject.Inject

class ApiWeatherMapper @Inject constructor(
    @ApiCurrentWeatherMapperAnnotation private val apiCurrentWeatherMapper: ApiMapper<CurrentWeather, ApiCurrentWeather>,
    @ApiDailyMapperAnnotation private val apiDailyMapper: ApiMapper<Daily, ApiDailyWeather>,
    @ApiHourlyMapperAnnotation private val apiHourlyMapper: ApiMapper<Hourly, ApiHourlyWeather>
) : ApiMapper<Weather, ApiWeather> {
    override fun mapToDomain(apiEntity: ApiWeather): Weather {
        return Weather(
            currentWeather = apiCurrentWeatherMapper.mapToDomain(apiEntity.apiCurrentWeather),
            daily = apiDailyMapper.mapToDomain(apiEntity.apiDailyWeather),
            hourly = apiHourlyMapper.mapToDomain(apiEntity.apiHourlyWeather),
        )
    }
}