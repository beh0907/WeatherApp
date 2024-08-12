package com.skymilk.weatherapp.utils

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.core.content.ContextCompat

class PermissionUtil {
    companion object {

        // 위치 권한이 부여되었는지 확인하는 함수
        fun isLocationPermissionsGranted(context: Context): Boolean {
            val fineLocationPermission = ContextCompat.checkSelfPermission(
                context,
                ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED

            val coarseLocationPermission = ContextCompat.checkSelfPermission(
                context,
                ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED

            return fineLocationPermission && coarseLocationPermission
        }

        // 위치 권한을 요청하는 함수
        @Composable
        fun RequestLocationPermissions(
            context: Context,
            onPermissionsResult: (Boolean) -> Unit
        ) {
            val permissionGrantedState: MutableState<Boolean> = remember {
                mutableStateOf(isLocationPermissionsGranted(context))
            }

            // 위치 권한을 요청하기 위한 런처(Launcher)
            val launcher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.RequestMultiplePermissions()
            ) { permissions ->
                // 모든 권한이 부여되었는지 확인
                val allPermissionsGranted = permissions.values.all { it }
                permissionGrantedState.value = allPermissionsGranted
                onPermissionsResult(allPermissionsGranted)
            }

            // 권한이 부여되지 않았을 경우 권한 요청 실행
            LaunchedEffect(Unit) {
                if (!permissionGrantedState.value) {
                    launcher.launch(
                        arrayOf(
                            ACCESS_FINE_LOCATION,
                            ACCESS_COARSE_LOCATION
                        )
                    )
                } else {
                    onPermissionsResult(true)
                }
            }
        }
    }
}