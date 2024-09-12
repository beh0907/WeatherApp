package com.skymilk.weatherapp.store.data.repository

import android.util.Log
import com.skymilk.weatherapp.store.data.mapper.ApiWeatherMapper
import com.skymilk.weatherapp.store.data.remote.WeatherApi
import com.skymilk.weatherapp.store.domain.models.Weather
import com.skymilk.weatherapp.store.domain.repository.WeatherRepository
import com.skymilk.weatherapp.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class WeatherRepositoryImpl @Inject constructor(
    private val weatherApi: WeatherApi,
    private val apiWeatherMapper: ApiWeatherMapper
) : WeatherRepository {
    override fun getWeatherData(location: Pair<Double, Double>): Flow<Resource<Weather>> = flow {
        emit(Resource.Loading())

        val apiWeather =
            weatherApi.getWeatherData(latitude = location.first, longitude = location.second)
        val weather = apiWeatherMapper.mapToDomain(apiWeather)

        Log.d("api 요청", location.toString())

        emit(Resource.Success(weather))
    }.catch { e ->
        e.printStackTrace()
        emit(Resource.Error(e.message.orEmpty()))
    }
}