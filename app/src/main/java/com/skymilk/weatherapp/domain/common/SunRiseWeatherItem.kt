package com.skymilk.weatherapp.domain.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
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
    modifier: Modifier = Modifier,
    weatherInfo: Daily.WeatherInfo
) {
    Card(
        Modifier.padding(horizontal = 8.dp)
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