package com.skymilk.weatherapp.presentation.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skymilk.weatherapp.domain.repository.DataStoreRepository
import com.skymilk.weatherapp.domain.repository.LocationRepository
import com.skymilk.weatherapp.domain.repository.WeatherRepository
import com.skymilk.weatherapp.utils.Event
import com.skymilk.weatherapp.utils.Resource
import com.skymilk.weatherapp.utils.Util
import com.skymilk.weatherapp.utils.sendEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val dataStoreRepository: DataStoreRepository,
    private val locationRepository: LocationRepository
) : ViewModel() {

    //홈 상태 정보
    private val _homeState = MutableStateFlow(HomeState())
    val homeState = _homeState.asStateFlow()

    // 권한과 GPS 상태를 각각 확인하기 위한 StateFlow
    private val _permissionsGranted = MutableStateFlow(false)
    val permissionsGranted: StateFlow<Boolean> = _permissionsGranted

    //GPS 활성화 여부
    val isGpsEnabled: StateFlow<Boolean> = locationRepository.isGpsEnabled()
        .stateIn(viewModelScope, SharingStarted.Lazily, false)

    init {
        viewModelScope.launch {
            //DataStore로부터 위치 정보 가져오기
            dataStoreRepository.getLocation().collectLatest { location ->

                //API로부터 날씨 정보 가져오기
                weatherRepository.getWeatherData(location).collectLatest { response ->
                    when (response) {
                        is Resource.Loading -> {
                            _homeState.update {
                                it.copy(isLoading = true)
                            }
                        }

                        is Resource.Success -> {


                            //정보 저장
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

                        is Resource.Error -> {
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

    // 권한과 GPS 상태에 따라 위치를 가져오는 함수
    fun checkPermissionsAndTrackingLocation(hasPermissions: Boolean) {
        _permissionsGranted.value = hasPermissions
        if (hasPermissions) {
            viewModelScope.launch {
                // GPS가 활성화된 경우에만 위치 정보를 가져옴
                if (isGpsEnabled.value) {
                    locationRepository.getCurrentLocation().collectLatest { location ->

                        Log.d("Viewmodel getCurrentLocation", location.toString())

                        location?.let {
                            dataStoreRepository.saveCurrentLocation(Pair(it.latitude, it.longitude))
                        }

                    }
                }
            }
        }
    }
}