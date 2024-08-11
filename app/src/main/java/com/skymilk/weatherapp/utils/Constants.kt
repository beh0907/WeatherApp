package com.skymilk.weatherapp.utils

object Constants {
    const val API_BASE_URL = "https://api.open-meteo.com/v1/"

    const val END_POINT = "forecast"

    const val LOCAL_DATA = "localData"

    const val APP_LOCATION_LATITUDE = "appLocationLatitude"

    const val APP_LOCATION_LONGITUDE = "appLocationLongitude"
}

object ApiParameter {
    const val LATITUDE = "latitude"
    const val LONGITUDE = "longitude"
    const val DAILY = "daily"
    const val CURRENT_WEATHER = "current"
    const val HOURLY = "hourly"
    const val TIME_FORMAT = "timeformat"
    const val TIMEZONE = "timezone"
}