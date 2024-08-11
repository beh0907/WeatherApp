package com.skymilk.weatherapp.data.repository

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.skymilk.weatherapp.domain.repository.LocalDataRepository
import com.skymilk.weatherapp.utils.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocalDataRepositoryImpl(
    private val context: Context
) : LocalDataRepository {
    override suspend fun saveCurrentLocation(location: Pair<Double, Double>) {
        context.dataStore.edit { local ->
            //위경도 정보 저장
            local[PreferencesKeys.APP_LOCATION_LATITUDE] = location.first
            local[PreferencesKeys.APP_LOCATION_LONGITUDE] = location.second
        }
    }

    override fun getLocation(): Flow<Pair<Double, Double>> {
        return context.dataStore.data.map { preferences ->

            // 기본값 설정 (저장된 값이 없을 때)
            val latitude = preferences[PreferencesKeys.APP_LOCATION_LATITUDE] ?: 37.3359
            val longitude = preferences[PreferencesKeys.APP_LOCATION_LONGITUDE] ?: 126.8056


            Log.d("Data Store 위치 정보", "$latitude $longitude")

            //결과 리턴
            Pair(latitude, longitude)
        }
    }
}


//dataStore 설정
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = Constants.LOCAL_DATA)

object PreferencesKeys {
    val APP_LOCATION_LATITUDE = doublePreferencesKey(name = Constants.APP_LOCATION_LATITUDE)

    val APP_LOCATION_LONGITUDE = doublePreferencesKey(name = Constants.APP_LOCATION_LONGITUDE)
}