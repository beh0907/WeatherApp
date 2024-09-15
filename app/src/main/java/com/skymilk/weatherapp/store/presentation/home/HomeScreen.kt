package com.skymilk.weatherapp.store.presentation.home

import android.content.IntentSender
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.android.gms.common.api.ResolvableApiException
import com.skymilk.weatherapp.MainActivity
import com.skymilk.weatherapp.store.domain.models.CurrentWeather
import com.skymilk.weatherapp.store.domain.models.Daily
import com.skymilk.weatherapp.store.domain.models.Hourly
import com.skymilk.weatherapp.store.presentation.common.SunRiseWeatherItem
import com.skymilk.weatherapp.store.presentation.common.UvWeatherItem
import com.skymilk.weatherapp.utils.DateUtil
import com.skymilk.weatherapp.utils.DateUtil.getCurrentHour
import kotlinx.coroutines.launch
import java.util.Date


@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    //collectAsStateWithLifecycle - 앱이 사용할 때만 플로우를 수집한다
    val homeState by homeViewModel.homeState.collectAsStateWithLifecycle()
    val gpsState by homeViewModel.gpsState.collectAsStateWithLifecycle()
    val permissionGranted by homeViewModel.permissionGranted.collectAsStateWithLifecycle()

    val context = LocalContext.current
    val scope = rememberCoroutineScope()


    //권한 허용 상태 확인
    LaunchedEffect(key1 = permissionGranted) {
        if (!permissionGranted) return@LaunchedEffect

        homeViewModel.checkLocationSettings(
            onSuccess = {
                Log.d("onSuccess", "onSuccess")
                homeViewModel.startTracking()
            },
            onFailure = { exception ->
                Log.d("exception", "exception")
                if (exception is ResolvableApiException) {
                    try {
                        exception.startResolutionForResult(context as MainActivity, 500)
                    } catch (sendEx: IntentSender.SendIntentException) {
                        // 설정 변경 요청 실패
                    }
                }
            }
        )
    }

    //GPS가 비활성화일 땐 위치정보를 가져오지 않는다
    LaunchedEffect(key1 = gpsState) {
        if (!permissionGranted) homeViewModel.stopTracking()
    }

    when (homeState.isLoading) {
        true -> {
            HomeScreenShimmer(modifier = modifier)
        }

        false -> {
            Box(
                modifier = modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {

//                Row(
//                    modifier = Modifier.align(Alignment.TopEnd)
//                ) {
////                    //GPS 상태 이미지
//                    Icon(
//                        painter = painterResource(id = if (networkState.isGpsEnabled) R.drawable.ic_location_on else R.drawable.ic_location_off),
//                        contentDescription = "is gps enabled"
//                    )
//                }

                IconButton(
                    modifier = Modifier.align(Alignment.TopEnd),
                    onClick = {
                        scope.launch {
                            homeViewModel.checkLocationSettings(
                                onSuccess = {
                                    homeViewModel.startTracking()
                                },
                                onFailure = { exception ->
                                    if (exception is ResolvableApiException) {
                                        try {
                                            exception.startResolutionForResult(
                                                context as MainActivity,
                                                500
                                            )
                                        } catch (sendEx: IntentSender.SendIntentException) {
                                            // 설정 변경 요청 실패
                                        }
                                    }
                                }
                            )
                        }
                    }
                ) {
                    Icon(imageVector = Icons.Default.Refresh, contentDescription = "refresh")
                }


                //날씨 정보가 있을떄
                homeState.weather?.let {
                    //현재 날씨 정보
                    CurrentWeatherSection(
                        modifier = Modifier.align(Alignment.TopCenter),
                        currentWeather = it.currentWeather
                    )

                    //시간별 날씨
                    HourlyWeatherSection(
                        modifier = Modifier.align(Alignment.BottomCenter), hourly = it.hourly
                    )
                }

                //필터링된 오늘 날씨 정보가 있을 때
                homeState.dailyWeatherInfo?.let {
                    DailyWeatherSection(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp),
                        dailyWeatherInfo = it
                    )
                }
            }
        }
    }
}

//현재 날씨 정보 영역
@Composable
fun CurrentWeatherSection(modifier: Modifier = Modifier, currentWeather: CurrentWeather) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        //현재 날씨 이미지
        Icon(
            painter = painterResource(id = currentWeather.weatherStatus.icon),
            contentDescription = currentWeather.weatherStatus.info,
            modifier = Modifier.size(120.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        //현재 온도
        Text(
            text = "${currentWeather.temperature}˚",
            style = MaterialTheme.typography.displayMedium
        )

        Spacer(modifier = Modifier.height(4.dp))

        //
        Text(
            text = "날씨 상태 : ${currentWeather.weatherStatus.info}",
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(2.dp))

        Text(
            text = "풍향 : ${currentWeather.windDirection} ${currentWeather.windSpeed} km/h",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

//오늘 날씨 정보 영역
@Composable
fun DailyWeatherSection(
    modifier: Modifier = Modifier,
    dailyWeatherInfo: Daily.WeatherInfo,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        SunRiseWeatherItem(weatherInfo = dailyWeatherInfo)

        Spacer(modifier = Modifier.width(16.dp))

        UvWeatherItem(weatherInfo = dailyWeatherInfo)
    }
}

//시간별 날씨 정보 영역
@Composable
fun HourlyWeatherSection(
    modifier: Modifier = Modifier,
    hourly: Hourly
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "시간별 날씨", style = MaterialTheme.typography.bodyMedium)

                Text(text = DateUtil.formatNormalDate("MM월 dd일", Date().time))
            }

            HorizontalDivider(
                modifier = Modifier.padding(horizontal = 16.dp),
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            //시간별 날씨 정보 리스트(가로 방향)
            LazyRow(
                modifier = Modifier.padding(16.dp)
            ) {

                //현재 시간대부터 24시간만큼만 목록에 표시
                val startIdx = getCurrentHour()
                val list = hourly.weatherInfo.drop(startIdx).take(24)

                items(list) { itemInfo ->
                    HourlyWeatherInfoItem(itemInfo = itemInfo)
                }
            }
        }
    }
}

//시간별 날씨 정보 아이템
@Composable
fun HourlyWeatherInfoItem(
    modifier: Modifier = Modifier,
    itemInfo: Hourly.HourlyInfoItem
) {
    Column(
        modifier = modifier.padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        //시간
        Text(text = itemInfo.time, style = MaterialTheme.typography.bodySmall)

        Spacer(modifier = Modifier.height(8.dp))

        //날씨 이미지
        Icon(
            painter = painterResource(id = itemInfo.weatherStatus.icon),
            contentDescription = itemInfo.weatherStatus.info
        )

        Spacer(modifier = Modifier.height(8.dp))

        //온도
        Text(text = "${itemInfo.temperature}˚", style = MaterialTheme.typography.bodySmall)
    }
}