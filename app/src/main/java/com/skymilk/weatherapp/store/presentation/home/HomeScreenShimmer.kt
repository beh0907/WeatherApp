package com.skymilk.weatherapp.store.presentation.home

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
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.skymilk.weatherapp.store.presentation.common.SunRiseWeatherItemShimmer
import com.skymilk.weatherapp.store.presentation.common.UvWeatherItemShimmer
import com.skymilk.weatherapp.store.presentation.common.shimmerEffect

@Composable
fun HomeScreenShimmer(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp), contentAlignment = Alignment.Center
    ) {
        //날씨 정보가 있을떄
        //현재 날씨 정보
        CurrentWeatherSectionShimmer(
            modifier = Modifier.align(Alignment.TopCenter)
        )

        //시간별 날씨
        HourlyWeatherSectionShimmer(
            modifier = Modifier.align(Alignment.BottomCenter)
        )

        //필터링된 오늘 날씨 정보가 있을 때
        DailyWeatherSectionShimmer(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        )
    }
}

//현재 날씨 정보 영역
@Composable
fun CurrentWeatherSectionShimmer(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        //현재 날씨 이미지
        Box(
            modifier = Modifier
                .size(120.dp)
                .shimmerEffect()
        )

        Spacer(modifier = Modifier.height(8.dp))

        //현재 온도
        Box(
            modifier = Modifier
                .height(36.dp)
                .width(100.dp)
                .shimmerEffect()
        )

        Spacer(modifier = Modifier.height(4.dp))

        //
        Box(
            modifier = Modifier
                .height(24.dp)
                .width(150.dp)
                .shimmerEffect()
        )

        Spacer(modifier = Modifier.height(4.dp))
        Box(
            modifier = Modifier
                .height(24.dp)
                .width(150.dp)
                .shimmerEffect()
        )
    }
}

//오늘 날씨 정보 영역
@Composable
fun DailyWeatherSectionShimmer(
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

//시간별 날씨 정보 영역
@Composable
fun HourlyWeatherSectionShimmer(
    modifier: Modifier = Modifier
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
                Box(
                    modifier = Modifier
                        .height(21.dp)
                        .width(50.dp)
                        .shimmerEffect()
                )

                Box(
                    modifier = Modifier
                        .height(21.dp)
                        .width(50.dp)
                        .shimmerEffect()
                )
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
                items(24) {
                    HourlyWeatherInfoItemShimmer()
                }
            }
        }
    }
}

//시간별 날씨 정보 아이템
@Composable
fun HourlyWeatherInfoItemShimmer(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(8.dp),
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