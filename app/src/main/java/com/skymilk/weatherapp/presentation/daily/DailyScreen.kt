package com.skymilk.weatherapp.presentation.daily

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.skymilk.weatherapp.R
import com.skymilk.weatherapp.presentation.common.SunRiseWeatherItem
import com.skymilk.weatherapp.presentation.common.UvWeatherItem
import com.skymilk.weatherapp.domain.models.Daily

@Composable
fun DailyScreen(
    modifier: Modifier = Modifier,
    dailyViewModel: DailyViewModel = hiltViewModel()
) {
    val dailyState = dailyViewModel.dailyState
    var selectedIndexWeather = remember {
        mutableIntStateOf(0)
    }

    //key1,2의 정보가 변할때 재실행한다
    val currentDailyWeatherItem = remember(key1 = selectedIndexWeather.value, key2 = dailyState) {
        dailyState.daily?.weatherInfo?.get(selectedIndexWeather.value)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        when (dailyState.isLoading) {
            true -> {
                CircularProgressIndicator()
            }

            false -> {
                //선택한 날씨의 최고/저 온도
                currentDailyWeatherItem?.let {
                    Text(
                        text = "최고 온도:${currentDailyWeatherItem.temperatureMax} 최저 온도:${currentDailyWeatherItem.temperatureMin}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "7일 날씨 예측",
                    style = MaterialTheme.typography.headlineSmall
                )

                Spacer(modifier = Modifier.height(8.dp))

                //일주일 날씨 정보 목록
                DailyWeatherList(dailyState, selectedIndexWeather)

                Spacer(modifier = Modifier.height(8.dp))

                //선택한 날짜의 바람 상태 정보
                SelectDailyWeatherWindSection(currentDailyWeatherItem)

                Spacer(modifier = Modifier.height(8.dp))

                //선택한 날씨 일출/몰, 자외선 정보
                currentDailyWeatherItem?.let {
                    SelectDailyWeatherSection(selectDailyWeatherInfo = it)
                }
            }
        }
    }
}

@Composable
private fun DailyWeatherList(
    dailyState: DailyState,
    selectedIndexWeather: MutableState<Int>
) {

    LazyRow(
        modifier = Modifier.fillMaxWidth()
    ) {
        dailyState.daily?.let { daily ->
            itemsIndexed(items = daily.weatherInfo) { index, itemInfo ->
                val color =
                    if (selectedIndexWeather.value == index) MaterialTheme.colorScheme.inverseSurface else CardDefaults.cardColors().containerColor

                DailyWeatherInfoItem(
                    itemInfo = itemInfo,
                    backgroundColor = color
                ) {
                    //선택한 아이템 인덱스를 저장
                    selectedIndexWeather.value = index
                }
            }
        }
    }
}

@Composable
private fun SelectDailyWeatherWindSection(currentDailyWeatherItem: Daily.WeatherInfo?) {
    currentDailyWeatherItem?.let {
        Card {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.wind_ic),
                        contentDescription = currentDailyWeatherItem.weatherStatus.info
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(text = "풍향 정보", style = MaterialTheme.typography.bodyMedium)
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "${it.windDirection} ${it.windSpeed} km/h",
                    style = MaterialTheme.typography.headlineMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

//선택한 날씨 정보 영역
@Composable
fun SelectDailyWeatherSection(
    modifier: Modifier = Modifier,
    selectDailyWeatherInfo: Daily.WeatherInfo,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        SunRiseWeatherItem(weatherInfo = selectDailyWeatherInfo)

        Spacer(modifier = Modifier.width(16.dp))

        UvWeatherItem(weatherInfo = selectDailyWeatherInfo)
    }
}

@Composable
fun DailyWeatherInfoItem(
    modifier: Modifier = Modifier,
    itemInfo: Daily.WeatherInfo,
    backgroundColor: Color,
    onClick: () -> Unit
) {
    Card(
        onClick = { onClick() },
        modifier = modifier.padding(8.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            //온도
            Text(text = "${itemInfo.temperatureMax} ˚", style = MaterialTheme.typography.bodySmall)

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
}