package com.skymilk.weatherapp.data.repository

import com.skymilk.weatherapp.data.mapper.ApiWeatherMapper
import com.skymilk.weatherapp.data.remote.WeatherApi
import com.skymilk.weatherapp.domain.models.Weather
import com.skymilk.weatherapp.domain.repository.WeatherRepository
import com.skymilk.weatherapp.utils.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherApi: WeatherApi,
    private val apiWeatherMapper: ApiWeatherMapper
) : WeatherRepository {
    override fun getWeatherData(): Flow<Response<Weather>> = flow {
        emit(Response.Loading())

        val apiWeather = weatherApi.getWeatherData()
        val weather = apiWeatherMapper.mapToDomain(apiWeather)

        emit(Response.Success(weather))
    }.catch { e ->
        e.printStackTrace()
        emit(Response.Error(e.message.orEmpty()))
    }
}