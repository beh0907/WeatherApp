package com.skymilk.weatherapp

import android.os.Bundle
import android.widget.Toast
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import com.skymilk.weatherapp.presentation.daily.DailyScreen
import com.skymilk.weatherapp.presentation.home.HomeScreen
import com.skymilk.weatherapp.ui.theme.WeatherAppTheme
import com.skymilk.weatherapp.presentation.navigation.Tabs
import com.skymilk.weatherapp.utils.EventBus.events
import com.skymilk.weatherapp.utils.Event
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WeatherAppTheme {

                //생명주기 탐지
                val lifecycle = LocalLifecycleOwner.current.lifecycle
                LaunchedEffect(key1 = lifecycle) {
                    //앱이 실행중일 경우에만 수집한다
                    repeatOnLifecycle(state = Lifecycle.State.STARTED) {
                        //알림 이벤트 수집 시 처리
                        events.collectLatest { event ->
                            when (event) {
                                is Event.Toast -> {
                                    Toast.makeText(
                                        this@MainActivity,
                                        event.message,
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }

                                is Event.SnackBar -> {}
                                is Event.Dialog -> {}
                                else -> Unit
                            }
                        }
                    }
                }

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