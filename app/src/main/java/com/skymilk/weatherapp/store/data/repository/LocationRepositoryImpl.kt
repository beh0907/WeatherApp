package com.skymilk.weatherapp.store.data.repository

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
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.Priority
import com.skymilk.weatherapp.store.data.remote.FusedLocationManager
import com.skymilk.weatherapp.store.domain.repository.LocationRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject


class LocationRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val fusedLocationManager: FusedLocationManager
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
            fusedLocationManager.requestLocationUpdates(locationRequest, locationCallback)
        } catch (e: SecurityException) {
            e.printStackTrace()
        }

        // Flow가 닫힐 때 위치 업데이트를 중지합니다.
        awaitClose {
            fusedLocationManager.removeLocationUpdates()
        }
    }.distinctUntilChanged() // 중복된 상태 변화 방지


    override fun checkLocationSettings(onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 100L).build()

        val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
        val client = LocationServices.getSettingsClient(context)
        val task = client.checkLocationSettings(builder.build())

        task.addOnSuccessListener {
            onSuccess()
        }

        task.addOnFailureListener { exception ->
            onFailure(exception)
        }
    }


    //GPS 활성화 상태 정보
    override fun getIsGpsEnabled(): Flow<Boolean> = callbackFlow {

        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        val gpsStatusReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                if (intent.action == LocationManager.PROVIDERS_CHANGED_ACTION) {
                    val isGpsEnabled =
                        locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)

                    trySend(isGpsEnabled)
                }
            }
        }

        //브로드캐스트 리시버 등록
        val intentFilter = IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION)
        context.registerReceiver(gpsStatusReceiver, intentFilter)

        //현재 상태값 체크
        val isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)

        trySend(isGpsEnabled)

        awaitClose {
            context.unregisterReceiver(gpsStatusReceiver)
        }
    }.distinctUntilChanged()  // 중복된 상태 변화 방지

    override suspend fun stopTracking() {
        fusedLocationManager.removeLocationUpdates()
    }
}