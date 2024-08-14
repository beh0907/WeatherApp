package com.skymilk.weatherapp.presentation.daily

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skymilk.weatherapp.domain.repository.DataStoreRepository
import com.skymilk.weatherapp.domain.repository.WeatherRepository
import com.skymilk.weatherapp.utils.Event
import com.skymilk.weatherapp.utils.Resource
import com.skymilk.weatherapp.utils.sendEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DailyViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {

    private val _dailyState = MutableStateFlow(DailyState())
    val dailyState = _dailyState.asStateFlow()

    init {
        viewModelScope.launch {
            //DataStore로부터 위치 정보 가져오기
            dataStoreRepository.getLocation().collectLatest { location ->

                //API로부터 날씨 정보 가져오기
                weatherRepository.getWeatherData(location).collectLatest { response ->
                    when (response) {
                        is Resource.Loading -> {
                            _dailyState.update {
                                it.copy(isLoading = true)
                            }
                        }

                        is Resource.Success -> {
                            _dailyState.update {
                                it.copy(
                                    isLoading = false,
                                    error = null,
                                    daily = response.data?.daily
                                )
                            }
                        }

                        is Resource.Error -> {
                            _dailyState.update {
                                it.copy(
                                    isLoading = false,
                                    error = response.message,
                                    daily = null
                                )
                            }

                            //에러 발생 알림 이벤트 전달
                            sendEvent(Event.Toast(response.message!!))
                        }
                    }
                }
            }
        }
    }
}