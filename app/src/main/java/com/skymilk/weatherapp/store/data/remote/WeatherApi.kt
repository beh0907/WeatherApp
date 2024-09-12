package com.skymilk.weatherapp.store.data.remote

import com.skymilk.weatherapp.store.data.remote.models.ApiWeather
import com.skymilk.weatherapp.utils.ApiParameter
import com.skymilk.weatherapp.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Query


interface WeatherApi {

    @GET(Constants.END_POINT)
    suspend fun getWeatherData(
        @Query(ApiParameter.LATITUDE) latitude: Double,
        @Query(ApiParameter.LONGITUDE) longitude: Double,
        @Query(ApiParameter.DAILY) daily: Array<String> = arrayOf(
            "weather_code",
            "temperature_2m_max",
            "temperature_2m_min",
            "sunrise,sunset",
            "uv_index_max",
            "wind_speed_10m_max",
            "wind_direction_10m_dominant"
        ),
        @Query(ApiParameter.CURRENT_WEATHER) current: Array<String> = arrayOf(
            "temperature_2m",
            "is_day",
            "weather_code",
            "wind_speed_10m",
            "wind_direction_10m"
        ),
        @Query(ApiParameter.HOURLY) hourly: Array<String> = arrayOf(
            "temperature_2m",
            "weather_code"
        ),
        @Query(ApiParameter.TIME_FORMAT) timeformat: String = "unixtime",
        @Query(ApiParameter.TIMEZONE) timezone: String = "Asia/Tokyo",
    ): ApiWeather

}

