package com.skymilk.weatherapp.presentation.daily

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skymilk.weatherapp.domain.repository.WeatherRepository
import com.skymilk.weatherapp.utils.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DailyViewModel @Inject constructor(
    private val repository: WeatherRepository
) : ViewModel() {

    var dailyState by mutableStateOf(DailyState())
        private set

    init {

        viewModelScope.launch {
            repository.getWeatherData().collectLatest { response ->
                when (response) {
                    is Response.Loading -> {
                        dailyState = dailyState.copy(isLoading = true)
                    }

                    is Response.Success -> {
                        dailyState = dailyState.copy(isLoading = false, error = null, daily = response.data?.daily)
                    }

                    is Response.Error -> {
                        dailyState = dailyState.copy(isLoading = false, error = response.message, daily = null)
                    }
                }
            }
        }
    }

}