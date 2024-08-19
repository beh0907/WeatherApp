package com.skymilk.weatherapp.data.repository

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.location.Location
import android.location.LocationManager
import android.util.Log
import com.google.android.gms.location.Granularity
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.Priority
import com.skymilk.weatherapp.data.remote.FusedLocationProvider
import com.skymilk.weatherapp.domain.repository.LocationRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val fusedLocationProvider: FusedLocationProvider
) : LocationRepository {

    @SuppressLint("MissingPermission")
    override fun getCurrentLocation(): Flow<Location?> = callbackFlow {
        // LocationCallback을 통해 위치 업데이트를 수신합니다.
        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                locationResult.lastLocation?.let {
                    Log.d("위치정보 업데이트", it.toString())
                    trySend(it)
                }
            }
        }

        val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 10000L)
            .apply {
                setMinUpdateDistanceMeters(1000f)//위치 업데이트 간의 최소 업데이트 거리
                setGranularity(Granularity.GRANULARITY_PERMISSION_LEVEL)
                setWaitForAccurateLocation(true)
            }.build()


        try {
            // 위치 업데이트를 요청합니다.
            fusedLocationProvider.requestLocationUpdates(locationRequest, locationCallback)
        } catch (e: SecurityException) {
            e.printStackTrace()
            // 위치 권한이 없는 경우 처리
        }

        // Flow가 닫힐 때 위치 업데이트를 중지합니다.
        awaitClose {
            fusedLocationProvider.removeLocationUpdates(locationCallback)
        }
    }.distinctUntilChanged() // 중복된 상태 변화 방지

//    @SuppressLint("MissingPermission")
//    override fun getCurrentLocation(): Flow<Location?> = flow {
//        val location = suspendCoroutine<Location?> { continuation ->
//            fusedLocationProvider.getCurrentLocationOnce(
//                onSuccessListener = { location ->
//                    Log.d("onSuccessListener", location.toString())
//                    continuation.resume(location)
//                },
//                onFailureListener = { exception ->
//                    Log.d("onFailureListener", exception.message.toString())
//                    continuation.resumeWithException(exception)
//                }
//            )
//        }
//        emit(location)
//    }

    override fun isGpsEnabled(): Flow<Boolean> = callbackFlow {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        val gpsStatusReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                if (intent.action == LocationManager.PROVIDERS_CHANGED_ACTION) {
                    val isEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                    trySend(isEnabled)
                }
            }
        }

        val intentFilter = IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION)
        context.registerReceiver(gpsStatusReceiver, intentFilter)

        // gps 상태 전달
        val isEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        trySend(isEnabled)

        awaitClose {
            context.unregisterReceiver(gpsStatusReceiver)
        }
    }.distinctUntilChanged()  // 중복된 상태 변화 방지

    override fun stopTracking() {
    }


}