package com.skymilk.weatherapp.utils

import androidx.annotation.DrawableRes
import com.skymilk.weatherapp.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object Util {
    //풍향 정보
    private val DIRECTIONS = listOf(
        "북풍",
        "북동풍",
        "동풍",
        "남동풍",
        "남풍",
        "남서풍",
        "서풍",
        "북서풍"
    )


    fun formatUnixDate(pattern: String, time: Long): String {
        val sdf = SimpleDateFormat(pattern, Locale.getDefault())
        return sdf.format(time * 1000)
    }

    fun formatNormalDate(pattern: String, time: Long): String {
        val sdf = SimpleDateFormat(pattern, Locale.getDefault())
        return sdf.format(time)
    }

    //방향 계산
    fun getWindDirection(windDirection: Double): String {
        return DIRECTIONS[(windDirection % 360 / 45 % 8).toInt()]
    }

    //코드값 변환
    fun getWeatherInfo(code: Int): WeatherInfoItem {
        return when (code) {
            0 -> WeatherInfoItem("맑은 하늘", R.drawable.clear_sky)
            1 -> WeatherInfoItem("대체로 맑음", R.drawable.mainly_clear)
            2 -> WeatherInfoItem("구름 조금", R.drawable.mainly_clear)
            3 -> WeatherInfoItem("흐림", R.drawable.over_cast)
            45, 48 -> WeatherInfoItem("안개", R.drawable.fog)
            51, 53, 55,
            -> WeatherInfoItem("이슬비", R.drawable.drizzle)

            56, 57 -> WeatherInfoItem("진눈깨비", R.drawable.freezing_drizzle)
            61,
            -> WeatherInfoItem("강우 : 조금", R.drawable.rain_slight)

            63 -> WeatherInfoItem("강우 : 보통", R.drawable.rain_heavy)
            65 -> WeatherInfoItem("강우 : 많음", R.drawable.rain_heavy)
            66, 67 -> WeatherInfoItem("우박", R.drawable.freezing_rain)
            71 -> WeatherInfoItem("강설 : 조금", R.drawable.snow_fall_slight)
            73 -> WeatherInfoItem("강설 : 보통", R.drawable.snow_fall_slight)
            75 -> WeatherInfoItem("강설 : 많음", R.drawable.snow_fall)
            77 -> WeatherInfoItem("싸락눈", R.drawable.snow_fall)
            80, 81, 82 -> WeatherInfoItem("소나기", R.drawable.rain_slight)
            85, 86 -> WeatherInfoItem("눈보라", R.drawable.snow_fall_slight)
            95, 96, 99 -> WeatherInfoItem("천둥번개", R.drawable.thunder_storm)
            else -> WeatherInfoItem("알 수 없음", R.drawable.clear_sky)
        }
    }

    fun isTodayDate(day: String): Boolean {
        val todayDate = formatNormalDate("E", Date().time)
        return todayDate.lowercase() == day.lowercase()
    }

}

data class WeatherInfoItem(
    val info: String,
    @DrawableRes val icon: Int
)