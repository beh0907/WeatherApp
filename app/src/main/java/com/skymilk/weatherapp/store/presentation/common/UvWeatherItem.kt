package com.skymilk.weatherapp.store.presentation.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.skymilk.weatherapp.store.domain.models.Daily

//자외선 지수 카드
@Composable
fun UvWeatherItem(
    weatherInfo: Daily.WeatherInfo
) {
    Card(
        modifier = Modifier.padding(horizontal = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "자외선 지수", style = MaterialTheme.typography.headlineSmall)

            Text(
                text = weatherInfo.uvIndex.toString(),
                style = MaterialTheme.typography.displayMedium
            )

            Text(
                text = "날씨 : ${weatherInfo.weatherStatus.info}",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

//자외선 지수 카드
@Composable
fun UvWeatherItemShimmer(
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.padding(horizontal = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(modifier = Modifier
                .height(24.dp)
                .fillMaxWidth()
                .shimmerEffect())

            Spacer(modifier = Modifier.height(8.dp))

            Box(modifier = Modifier
                .height(48.dp)
                .fillMaxWidth()
                .shimmerEffect())

            Spacer(modifier = Modifier.height(8.dp))

            Box(modifier = Modifier
                .height(24.dp)
                .fillMaxWidth()
                .shimmerEffect())
        }
    }
}