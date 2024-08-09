package com.skymilk.weatherapp.presentation.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skymilk.weatherapp.domain.repository.WeatherRepository
import com.skymilk.weatherapp.utils.Response
import com.skymilk.weatherapp.utils.Util
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: WeatherRepository
) : ViewModel() {

    //홈 상태 정보
    var homeState by mutableStateOf(HomeState())
        private set

    init {
        viewModelScope.launch {
            repository.getWeatherData().collect { response ->
                when (response) {
                    is Response.Loading -> {
                        homeState = homeState.copy(isLoading = true)
                    }

                    is Response.Success -> {
                        homeState =
                            homeState.copy(isLoading = false, weather = response.data, error = null)

                        //오늘 날씨 정보를 필터링한다
                        val todayWeatherInfo = response.data?.daily?.weatherInfo?.find {
                            Util.isTodayDate(it.time)
                        }
                        homeState = homeState.copy(dailyWeatherInfo = todayWeatherInfo)

                    }

                    is Response.Error -> {
                        homeState = homeState.copy(
                            isLoading = false,
                            weather = null,
                            error = response.message
                        )
                    }
                }
            }
        }
    }
}