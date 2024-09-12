package com.skymilk.weatherapp.utils

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import com.gun0912.tedpermission.coroutine.TedPermission

object PermissionUtil {

    //코루틴 권한 요청 처리
    suspend fun requestLocationPermissions(): Boolean {
        val permissions = listOf(
            ACCESS_COARSE_LOCATION,
            ACCESS_FINE_LOCATION
        )

        //권한 요청 및 결과 정보 리턴
        return TedPermission.create()
            .setPermissions(*permissions.toTypedArray())
            .checkGranted()
    }
}