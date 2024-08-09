package com.skymilk.weatherapp.presentation.home

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
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.skymilk.weatherapp.domain.common.SunRiseWeatherItem
import com.skymilk.weatherapp.domain.common.UvWeatherItem
import com.skymilk.weatherapp.domain.models.CurrentWeather
import com.skymilk.weatherapp.domain.models.Daily
import com.skymilk.weatherapp.domain.models.Hourly
import com.skymilk.weatherapp.utils.Util
import java.util.Date

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel = hiltViewModel()
) {

    val homeState = homeViewModel.homeState
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp), contentAlignment = Alignment.Center
    ) {
        when (homeState.isLoading) {
            true -> {
                CircularProgressIndicator()
            }

            false -> {
                //날씨 정보가 있을떄
                homeState.weather?.let {
                    //현재 날씨 정보
                    CurrentWeatherSection(
                        modifier = Modifier.align(Alignment.TopCenter),
                        currentWeather = it.currentWeather
                    )

                    //시간별 날씨
                    HourlyWeatherSection(
                        modifier = Modifier.align(Alignment.BottomCenter),
                        hourly = it.hourly
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
            text = "${currentWeather.temperature} ˚",
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

                Text(text = Util.formatNormalDate("MM월 dd일", Date().time))
            }

            HorizontalDivider(
                thickness = 2.dp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            //시간별 날씨 정보 리스트(가로 방향)
            LazyRow(
                modifier = Modifier.padding(16.dp)
            ) {

                items(hourly.weatherInfo) { itemInfo ->
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
        //온도
        Text(text = "${itemInfo.temperature} ˚", style = MaterialTheme.typography.bodySmall)

        Spacer(modifier = Modifier.height(8.dp))

        //날씨 이미지
        Icon(
            painter = painterResource(id = itemInfo.weatherStatus.icon),
            contentDescription = itemInfo.weatherStatus.info
        )

        Spacer(modifier = Modifier.height(8.dp))

        //시간
        Text(text = itemInfo.time, style = MaterialTheme.typography.bodySmall)
    }
}
