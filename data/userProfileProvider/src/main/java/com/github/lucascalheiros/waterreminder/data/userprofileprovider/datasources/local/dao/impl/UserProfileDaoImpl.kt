package com.github.lucascalheiros.waterreminder.data.userprofileprovider.datasources.local.dao.impl

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.github.lucascalheiros.waterreminder.data.userprofileprovider.datasources.local.dao.UserProfileDao
import com.github.lucascalheiros.waterreminder.data.userprofileprovider.models.AmbienceTemperatureLevelDb
import com.github.lucascalheiros.waterreminder.data.userprofileprovider.models.AmbienceTemperatureLevelDb.Companion.toAmbienceTemperatureLevelDb
import com.github.lucascalheiros.waterreminder.data.userprofileprovider.models.UserProfileDb
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map


class UserProfileDaoImpl(
    private val dataStore: DataStore<Preferences>
) : UserProfileDao {
    override suspend fun getUserProfile(): UserProfileDb {
        return dataStore.data.first().getUserProfileDb()
    }

    override fun getUserProfileFlow(): Flow<UserProfileDb> {
        return dataStore.data.map { it.getUserProfileDb() }
    }

    override suspend fun save(userProfileDb: UserProfileDb) {
        dataStore.edit {
            it[nameKey] = userProfileDb.name
            it[weightInGramsKey] = userProfileDb.weightInGrams
            it[activityLevelInWeekDaysKey] = userProfileDb.activityLevelInWeekDays
            it[temperatureLevelKey] = userProfileDb.temperatureLevel.value
        }
    }

    private fun Preferences?.getUserProfileDb(): UserProfileDb {
        return UserProfileDb(
            name = this?.get(nameKey).orEmpty(),
            weightInGrams = this?.get(weightInGramsKey) ?: 70000.0,
            activityLevelInWeekDays = this?.get(activityLevelInWeekDaysKey) ?: 0,
            temperatureLevel = this?.get(temperatureLevelKey)?.toAmbienceTemperatureLevelDb()
                ?: AmbienceTemperatureLevelDb.Moderate
        )
    }

    companion object {
        val nameKey = stringPreferencesKey("USERPROFILE_NAME")
        val weightInGramsKey = doublePreferencesKey("USERPROFILE_WEIGHT_IN_GRAMS")
        val activityLevelInWeekDaysKey = intPreferencesKey("USERPROFILE_ACTIVITY_LEVEL")
        val temperatureLevelKey = intPreferencesKey("USERPROFILE_TEMPERATURE_LEVEL")
    }

}