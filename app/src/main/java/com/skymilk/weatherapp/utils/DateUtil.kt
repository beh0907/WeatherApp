package com.skymilk.weatherapp.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateUtil {
    fun formatUnixDate(pattern: String, time: Long): String {
        val sdf = SimpleDateFormat(pattern, Locale.getDefault())
        return sdf.format(time * 1000)
    }

    fun formatNormalDate(pattern: String, time: Long): String {
        val sdf = SimpleDateFormat(pattern, Locale.getDefault())
        return sdf.format(time)
    }

    //현재 hour 정보
    fun getCurrentHour(): Int {
        val hour = formatNormalDate("HH", Date().time)
        return hour.toInt()
    }

    fun isTodayDate(day: String): Boolean {
        val todayDate = formatNormalDate("E", Date().time)
        return todayDate.lowercase() == day.lowercase()
    }

}