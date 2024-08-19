package com.skymilk.weatherapp.presentation.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.skymilk.weatherapp.domain.models.Daily

//일출/일몰 카드
@Composable
fun SunRiseWeatherItem(
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
            Text(text = "일출", style = MaterialTheme.typography.headlineSmall)

            Text(
                text = weatherInfo.sunrise,
                style = MaterialTheme.typography.displayMedium
            )

            Text(
                text = "일몰 ${weatherInfo.sunset}",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

//일출/일몰 카드
@Composable
fun SunRiseWeatherItemShimmer(
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