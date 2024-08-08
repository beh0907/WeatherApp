package com.skymilk.weatherapp.di

import com.skymilk.weatherapp.data.mapper.ApiMapper
import com.skymilk.weatherapp.data.mapper.ApiDailyMapper
import com.skymilk.weatherapp.data.mapper.ApiHourlyMapper
import com.skymilk.weatherapp.data.mapper.ApiWeatherMapper
import com.skymilk.weatherapp.data.mapper.CurrentWeatherMapper
import com.skymilk.weatherapp.data.remote.models.ApiCurrentWeather
import com.skymilk.weatherapp.data.remote.models.ApiDailyWeather
import com.skymilk.weatherapp.data.remote.models.ApiHourlyWeather
import com.skymilk.weatherapp.data.remote.models.ApiWeather
import com.skymilk.weatherapp.domain.models.CurrentWeather
import com.skymilk.weatherapp.domain.models.Daily
import com.skymilk.weatherapp.domain.models.Hourly
import com.skymilk.weatherapp.domain.models.Weather
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier

@Module
@InstallIn(SingletonComponent::class)
object MapperModule {

    @Provides
    @ApiCurrentWeatherMapperAnnotation
    fun provideCurrentWeatherMapper(): ApiMapper<CurrentWeather, ApiCurrentWeather> =
        CurrentWeatherMapper()

    @Provides
    @ApiDailyMapperAnnotation
    fun provideApiDailyMapper(): ApiMapper<Daily, ApiDailyWeather> = ApiDailyMapper()

    @Provides
    @ApiHourlyMapperAnnotation
    fun provideApiHourlyMapper(): ApiMapper<Hourly, ApiHourlyWeather> = ApiHourlyMapper()

    @ApiWeatherMapperAnnotation
    @Provides
    fun provideApiWeatherMapper(
        apiCurrentWeatherMapper: ApiMapper<CurrentWeather, ApiCurrentWeather>,
        apiDailyMapper: ApiMapper<Daily, ApiDailyWeather>,
        apiHourlyMapper: ApiMapper<Hourly, ApiHourlyWeather>,
    ): ApiMapper<Weather, ApiWeather> {
        return ApiWeatherMapper(
            apiCurrentWeatherMapper,
            apiDailyMapper,
            apiHourlyMapper,
        )
    }
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ApiWeatherMapperAnnotation

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ApiDailyMapperAnnotation

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ApiHourlyMapperAnnotation

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ApiCurrentWeatherMapperAnnotation