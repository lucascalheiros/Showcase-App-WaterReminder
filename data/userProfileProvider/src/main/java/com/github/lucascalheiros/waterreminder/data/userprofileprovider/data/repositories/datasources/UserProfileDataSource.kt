package com.github.lucascalheiros.waterreminder.data.userprofileprovider.data.repositories.datasources

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.github.lucascalheiros.waterreminder.data.userprofileprovider.data.models.ActivityLevelDb
import com.github.lucascalheiros.waterreminder.data.userprofileprovider.data.models.ActivityLevelDb.Companion.toActivityLevelDb
import com.github.lucascalheiros.waterreminder.data.userprofileprovider.data.models.AmbienceTemperatureLevelDb
import com.github.lucascalheiros.waterreminder.data.userprofileprovider.data.models.AmbienceTemperatureLevelDb.Companion.toAmbienceTemperatureLevelDb
import com.github.lucascalheiros.waterreminder.data.userprofileprovider.data.models.UserProfileDb
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map


internal class UserProfileDataSource(
    private val dataStore: DataStore<Preferences>
) {
    suspend fun getUserProfile(): UserProfileDb {
        return dataStore.data.first().getUserProfileDb()
    }

    fun getUserProfileFlow(): Flow<UserProfileDb> {
        return dataStore.data.map {
            it.getUserProfileDb()
        }
    }

    suspend fun save(userProfileDb: UserProfileDb) {
        dataStore.edit {
            it[nameKey] = userProfileDb.name
            it[weightInGramsKey] = userProfileDb.weightInGrams
            it[activityLevelKey] = userProfileDb.activityLevel.value
            it[temperatureLevelKey] = userProfileDb.temperatureLevel.value
        }
    }

    private fun Preferences?.getUserProfileDb(): UserProfileDb {
        return UserProfileDb(
            name = this?.get(nameKey).orEmpty(),
            weightInGrams = this?.get(weightInGramsKey) ?: 70000.0,
            activityLevel = this?.get(activityLevelKey)?.toActivityLevelDb()
                ?: ActivityLevelDb.Light,
            temperatureLevel = this?.get(temperatureLevelKey)?.toAmbienceTemperatureLevelDb()
                ?: AmbienceTemperatureLevelDb.Moderate
        )
    }

    companion object {
        val nameKey = stringPreferencesKey("USERPROFILE_NAME")
        val weightInGramsKey = doublePreferencesKey("USERPROFILE_WEIGHT_IN_GRAMS")
        val activityLevelKey = intPreferencesKey("USERPROFILE_ACTIVITY_LEVEL")
        val temperatureLevelKey = intPreferencesKey("USERPROFILE_TEMPERATURE_LEVEL")
    }

}