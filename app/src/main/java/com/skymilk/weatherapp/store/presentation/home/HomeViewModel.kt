package com.skymilk.weatherapp.store.presentation.home

import android.location.Location
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skymilk.weatherapp.store.domain.usecase.datastore.DataStoreUseCase
import com.skymilk.weatherapp.store.domain.usecase.location.LocationUseCase
import com.skymilk.weatherapp.store.domain.usecase.weather.WeatherUseCase
import com.skymilk.weatherapp.utils.DateUtil
import com.skymilk.weatherapp.utils.Event
import com.skymilk.weatherapp.utils.PermissionUtil
import com.skymilk.weatherapp.utils.Resource
import com.skymilk.weatherapp.utils.sendEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val weatherUseCase: WeatherUseCase,
    private val dataStoreUseCase: DataStoreUseCase,
    private val locationUseCase: LocationUseCase
) : ViewModel() {

    //홈 상태 정보
    private val _homeState = MutableStateFlow(HomeState())
    val homeState = _homeState.asStateFlow()

    //GPS
    val gpsState: StateFlow<Boolean> = locationUseCase.getIsGpsEnabled()
        .stateIn(viewModelScope, SharingStarted.Lazily, false)

    //권한 허용 여부
    private val _permissionGranted = MutableStateFlow(false)
    val permissionGranted = _permissionGranted.asStateFlow()

    //측위된 위치 정보
    private val _fusedLocation = MutableSharedFlow<Location>()
    val fusedLocation = _fusedLocation.asSharedFlow()

    init {
        viewModelScope.launch {
            _permissionGranted.update { PermissionUtil.requestLocationPermissions() }
        }

        viewModelScope.launch {
            //DataStore로부터 위치 정보 가져오기
            dataStoreUseCase.getLocation().collectLatest { location ->

                //API로부터 날씨 정보 가져오기
                weatherUseCase.getWeatherData(location).collectLatest { response ->
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
                                DateUtil.isTodayDate(it.time)
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

    //시작
    fun startTracking() {
        viewModelScope.launch {
            //권한 요청 및 체크
            if (!PermissionUtil.requestLocationPermissions()) {
                sendEvent(Event.Toast("GPS 권한을 허용해주세요"))
                return@launch
            }

            //위치 정보 상태 체크
            if (!gpsState.value) {
                sendEvent(Event.Toast("GPS 상태를 확인해주세요"))
                return@launch
            }

            //위치 정보 수집
            locationUseCase.getCurrentLocation().collectLatest { location ->
                location?.let {
                    //수집된 위치 정보 저장
                    _fusedLocation.emit(location)

                    //수집된 위치 정보 dataStore 저장
                    dataStoreUseCase.saveCurrentLocation(Pair(it.latitude, it.longitude))
                }
            }

        }
    }

    //중지
    fun stopTracking() {
        viewModelScope.launch {
            locationUseCase.stopTracking()
        }

    }

    fun checkLocationSettings(onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        locationUseCase.checkLocationSettings(onSuccess, onFailure)
    }
}