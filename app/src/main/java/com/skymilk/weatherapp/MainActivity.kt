package com.skymilk.weatherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.skymilk.weatherapp.presentation.daily.DailyScreen
import com.skymilk.weatherapp.presentation.home.HomeScreen
import com.skymilk.weatherapp.ui.theme.WeatherAppTheme
import com.skymilk.weatherapp.presentation.common.Tabs
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WeatherAppTheme {
                WeatherApp()
            }
        }
    }

    @Composable
    fun WeatherApp() {
        //rememberSaveable - 가로세로전환과 같은 앱 구성 관련 변화가 발생할 때도 정보를 유지
        var selectedIndex by rememberSaveable {
            mutableIntStateOf(0)
        }

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            bottomBar = {
                WeatherAppBottomNavigation(tabs = Tabs.entries, selectedIndex = selectedIndex) {
                    selectedIndex = it
                }
            }
        ) { innerPadding ->
            when(selectedIndex) {
                0 -> {
                    HomeScreen(modifier = Modifier.padding(innerPadding))
                }

                1 -> {
                    DailyScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }

    //하단 메뉴 바 네비게이션
    @Composable
    fun WeatherAppBottomNavigation(
        modifier: Modifier = Modifier,
        tabs: List<Tabs>,
        selectedIndex: Int,
        onSelectedChange: (Int) -> Unit
    ) {
        NavigationBar(modifier = modifier) {
            tabs.forEachIndexed { index, tab ->
                NavigationBarItem(
                    selected = index == selectedIndex,
                    onClick = { onSelectedChange(index) },
                    icon = { Icon(imageVector = tab.icon, contentDescription = tab.title) },
                    label = { Text(text = tab.title) }
                )
            }
        }
    }
}