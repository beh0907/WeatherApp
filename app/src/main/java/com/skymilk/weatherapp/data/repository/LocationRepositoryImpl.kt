package com.skymilk.weatherapp.data.repository

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.skymilk.weatherapp.domain.repository.LocationRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class LocationRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val fusedLocationProviderClient: FusedLocationProviderClient
) : LocationRepository {

    @SuppressLint("MissingPermission")
    override fun getCurrentLocation(): Flow<Location?> = flow {
        val location = suspendCoroutine<Location?> { continuation ->
            fusedLocationProviderClient.lastLocation
                .addOnSuccessListener { location ->
                    continuation.resume(location)
                }
                .addOnFailureListener { exception ->
                    continuation.resumeWithException(exception)
                }
        }
        emit(location)
    }

    override fun isGpsEnabled(): Flow<Boolean> = flow {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        emit(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
    }
}