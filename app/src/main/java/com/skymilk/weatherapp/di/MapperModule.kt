package com.skymilk.weatherapp.di

import com.skymilk.weatherapp.store.data.mapper.ApiHourlyMapper
import com.skymilk.weatherapp.store.data.mapper.ApiMapper
import com.skymilk.weatherapp.store.data.mapper.ApiWeatherMapper
import com.skymilk.weatherapp.store.data.mapper.CurrentWeatherMapper
import com.skymilk.weatherapp.store.data.remote.models.ApiCurrentWeather
import com.skymilk.weatherapp.store.data.remote.models.ApiDailyWeather
import com.skymilk.weatherapp.store.data.remote.models.ApiHourlyWeather
import com.skymilk.weatherapp.store.data.remote.models.ApiWeather
import com.skymilk.weatherapp.store.domain.models.CurrentWeather
import com.skymilk.weatherapp.store.domain.models.Daily
import com.skymilk.weatherapp.store.domain.models.Hourly
import com.skymilk.weatherapp.store.domain.models.Weather
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
    fun provideApiDailyMapper(): ApiMapper<Daily, ApiDailyWeather> =
        com.skymilk.weatherapp.store.data.mapper.ApiDailyMapper()

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