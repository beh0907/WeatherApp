package com.skymilk.weatherapp.presentation.home

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skymilk.weatherapp.domain.repository.LocalDataRepository
import com.skymilk.weatherapp.domain.repository.WeatherRepository
import com.skymilk.weatherapp.utils.Event
import com.skymilk.weatherapp.utils.PermissionUtil
import com.skymilk.weatherapp.utils.Response
import com.skymilk.weatherapp.utils.Util
import com.skymilk.weatherapp.utils.sendEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val localDataRepository: LocalDataRepository,
    @ApplicationContext context: Context
) : ViewModel() {

    //홈 상태 정보
    private val _homeState = MutableStateFlow(HomeState())
    val homeState = _homeState.asStateFlow()

    init {
        viewModelScope.launch {
            //DataStore로부터 위치 정보 가져오기
            localDataRepository.getLocation().collectLatest { location ->

                //API로부터 날씨 정보 가져오기
                weatherRepository.getWeatherData(location).collectLatest { response ->
                    when (response) {
                        is Response.Loading -> {
                            _homeState.update {
                                it.copy(isLoading = true)
                            }
                        }

                        is Response.Success -> {

                            _homeState.update {
                                it.copy(
                                    isLoading = false,
                                    weather = response.data,
                                    error = null
                                )
                            }

                            //오늘 날씨 정보를 필터링한다
                            val todayWeatherInfo = response.data?.daily?.weatherInfo?.find {
                                Util.isTodayDate(it.time)
                            }
                            _homeState.update {
                                it.copy(dailyWeatherInfo = todayWeatherInfo)
                            }
                        }

                        is Response.Error -> {
                            _homeState.update {
                                it.copy(
                                    isLoading = false,
                                    weather = null,
                                    error = response.message
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

    fun refreshLocation() {
        viewModelScope.launch {
            val latitude = Random.nextDouble(-90.0, 90.0)
            val longitude = Random.nextDouble(-180.0, 180.0)

            localDataRepository.saveCurrentLocation(Pair(latitude, longitude))
        }
    }
}