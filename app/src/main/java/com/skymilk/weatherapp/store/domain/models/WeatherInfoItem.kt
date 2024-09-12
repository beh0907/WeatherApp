package com.skymilk.weatherapp.store.domain.models

import androidx.annotation.DrawableRes

data class WeatherInfoItem(
    val info: String,
    @DrawableRes val icon: Int
)
