package com.skymilk.weatherapp.presentation.daily

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.skymilk.weatherapp.presentation.common.SunRiseWeatherItemShimmer
import com.skymilk.weatherapp.presentation.common.UvWeatherItemShimmer
import com.skymilk.weatherapp.presentation.common.shimmerEffect

@Composable
fun DailyScreenShimmer(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        //선택한 날씨의 최고/저 온도
        Box(
            modifier = Modifier
                .height(20.dp)
                .width(200.dp)
                .shimmerEffect()
        )

        Spacer(modifier = Modifier.height(16.dp))
        Box(
            modifier = Modifier
                .height(30.dp)
                .width(150.dp)
                .shimmerEffect()
        )

        Spacer(modifier = Modifier.height(8.dp))

        //일주일 날씨 정보 목록
        DailyWeatherListShimmer()

        Spacer(modifier = Modifier.height(8.dp))

        //선택한 날짜의 바람 상태 정보
        SelectDailyWeatherWindSectionShimmer()

        Spacer(modifier = Modifier.height(8.dp))

        //선택한 날씨 일출/몰, 자외선 정보
        SelectDailyWeatherSectionShimmer(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        )
    }
}

@Composable
private fun DailyWeatherListShimmer() {
    LazyRow(
        modifier = Modifier.fillMaxWidth()
    ) {
        items(7) {
            DailyWeatherInfoItemShimmer(
                backgroundColor = CardDefaults.cardColors().containerColor
            )
        }
    }
}

@Composable
private fun SelectDailyWeatherWindSectionShimmer() {
    Card {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Box(
                modifier = Modifier
                    .height(40.dp)
                    .fillMaxWidth()
                    .shimmerEffect()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .height(40.dp)
                    .fillMaxWidth()
                    .shimmerEffect()
            )
        }
    }
}

//선택한 날씨 정보 영역
@Composable
fun SelectDailyWeatherSectionShimmer(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        SunRiseWeatherItemShimmer(modifier = Modifier.weight(1f))

        Spacer(modifier = Modifier.width(16.dp))

        UvWeatherItemShimmer(modifier = Modifier.weight(1f))
    }
}

@Composable
fun DailyWeatherInfoItemShimmer(
    modifier: Modifier = Modifier,
    backgroundColor: Color
) {
    Card(
        modifier = modifier.padding(8.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .height(16.dp)
                    .width(30.dp)
                    .shimmerEffect()
            )

            Spacer(modifier = Modifier.height(8.dp))

            Box(
                modifier = Modifier
                    .height(24.dp)
                    .width(50.dp)
                    .shimmerEffect()
            )

            Spacer(modifier = Modifier.height(8.dp))

            Box(
                modifier = Modifier
                    .height(16.dp)
                    .width(30.dp)
                    .shimmerEffect()
            )
        }
    }
}