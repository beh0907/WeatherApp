package com.skymilk.weatherapp.presentation.daily

import com.skymilk.weatherapp.domain.models.Daily
import com.skymilk.weatherapp.domain.models.Weather

data class DailyState(
    val daily: Daily? = null,
    val error:String? = null,
    val isLoading:Boolean = false,
)