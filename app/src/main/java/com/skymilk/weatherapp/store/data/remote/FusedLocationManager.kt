package com.skymilk.weatherapp.store.data.remote

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.os.Looper
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener

class FusedLocationManager(private val context: Context) {

    private var isLocationUpdateRequested: Boolean = false
    private val fusedLocationProviderClient: FusedLocationProviderClient by lazy {
        LocationServices.getFusedLocationProviderClient(context)
    }
    private var locationCallback: LocationCallback? = null

    fun requestLocationUpdates(
        locationRequest: LocationRequest,
        locationCallback: LocationCallback
    ) {
        //동작중인 콜백이 있다면 제거
        removeLocationUpdates()

        try {
            // 위치 업데이트 요청
            fusedLocationProviderClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper()
            )

            this.locationCallback = locationCallback

            isLocationUpdateRequested = true
        } catch (e: SecurityException) {
            e.printStackTrace()
            // 위치 권한이 없는 경우 처리
        }
    }

    fun removeLocationUpdates() {//위치 업데이트 요청을 중지
        if (isLocationUpdateRequested) {
            fusedLocationProviderClient.removeLocationUpdates(locationCallback!!)
            locationCallback = null
            isLocationUpdateRequested = false
        }
    }

    @SuppressLint("MissingPermission")
    fun getCurrentLocationOnce(
        onSuccessListener: OnSuccessListener<Location>,
        onFailureListener: OnFailureListener,
    ) {
        //일회용 request
        fusedLocationProviderClient.lastLocation
            .addOnSuccessListener(onSuccessListener)
            .addOnFailureListener(onFailureListener)
    }
}