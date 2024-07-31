package com.github.lucascalheiros.waterreminder.data.themewrapper.data.repositories.datasources

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import com.github.lucascalheiros.waterreminder.data.themewrapper.data.models.ThemeOptions
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class ThemeDataSource(
    private val dataStore: DataStore<Preferences>,
    private val themeWrapper: ThemeWrapper
) {

    fun getTheme(): Flow<ThemeOptions> = dataStore.data.map {
        it[themeValuePreferenceKey]?.let { it1 -> ThemeOptions.fromValue(it1) } ?: ThemeOptions.Auto
    }

    suspend fun setTheme(appTheme: ThemeOptions) {
        themeWrapper.setTheme(appTheme)
        dataStore.edit {
            it[themeValuePreferenceKey] = appTheme.value
        }
    }

    companion object {
        private val themeValuePreferenceKey = intPreferencesKey("themeValuePreferenceKey")
    }
}