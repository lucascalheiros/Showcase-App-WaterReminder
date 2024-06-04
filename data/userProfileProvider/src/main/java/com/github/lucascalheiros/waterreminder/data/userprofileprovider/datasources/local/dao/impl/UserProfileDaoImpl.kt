package com.github.lucascalheiros.waterreminder.data.userprofileprovider.datasources.local.dao.impl

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.github.lucascalheiros.waterreminder.data.userprofileprovider.datasources.local.dao.UserProfileDao
import com.github.lucascalheiros.waterreminder.data.userprofileprovider.models.AmbienceTemperatureLevelDb.Companion.toAmbienceTemperatureLevelDb
import com.github.lucascalheiros.waterreminder.data.userprofileprovider.models.UserProfileDb
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map


class UserProfileDaoImpl(
    private val dataStore: DataStore<Preferences>
) : UserProfileDao {
    override suspend fun getUserProfile(): UserProfileDb? {
        return dataStore.data.first().let {
            UserProfileDb(
                it[nameKey] ?: return null,
                it[weightInGramsKey] ?: return null,
                it[activityLevelInWeekDaysKey] ?: return null,
                it[temperatureLevelKey]?.toAmbienceTemperatureLevelDb() ?: return null,
            )
        }
    }

    override fun getUserProfileFlow(): Flow<UserProfileDb?> {
        return dataStore.data.map {
            UserProfileDb(
                it[nameKey] ?: return@map null,
                it[weightInGramsKey] ?: return@map null,
                it[activityLevelInWeekDaysKey] ?: return@map null,
                it[temperatureLevelKey]?.toAmbienceTemperatureLevelDb() ?: return@map null,
            )
        }
    }

    override suspend fun save(userProfileDb: UserProfileDb) {
        dataStore.edit {
            it[nameKey] = userProfileDb.name
            it[weightInGramsKey] = userProfileDb.weightInGrams
            it[activityLevelInWeekDaysKey] = userProfileDb.activityLevelInWeekDays
            it[temperatureLevelKey] = userProfileDb.temperatureLevel.value
        }
    }

    companion object {
        val nameKey = stringPreferencesKey("USERPROFILE_NAME")
        val weightInGramsKey = doublePreferencesKey("USERPROFILE_WEIGHT_IN_GRAMS")
        val activityLevelInWeekDaysKey = intPreferencesKey("USERPROFILE_ACTIVITY_LEVEL")
        val temperatureLevelKey = intPreferencesKey("USERPROFILE_TEMPERATURE_LEVEL")
    }

}