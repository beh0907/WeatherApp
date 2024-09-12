package com.skymilk.weatherapp.store.presentation.daily

import com.skymilk.weatherapp.store.domain.models.Daily


data class DailyState(
    val daily: Daily? = null,
    val error: String? = null,
    val isLoading: Boolean = false,
)