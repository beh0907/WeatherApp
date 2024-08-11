package com.skymilk.weatherapp.presentation.common

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector

//네비게이션 바 메뉴 정의
enum class Tabs(
    val title: String,
    val icon: ImageVector
) {
    HOME(
        title = "Home",
        icon = Icons.Default.Home
    ),
    DAILY(
        title = "Daily",
        icon = Icons.Default.DateRange
    )
}