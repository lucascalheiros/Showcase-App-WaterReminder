package com.github.lucascalheiros.waterreminder.data.themewrapper.data.impl

import android.app.UiModeManager
import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import com.github.lucascalheiros.waterreminder.data.themewrapper.data.ThemeWrapper
import com.github.lucascalheiros.waterreminder.data.themewrapper.models.ThemeOptions
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class ThemeWrapperImpl(
    private val dataStore: DataStore<Preferences>,
    private val context: Context
): ThemeWrapper {

    override fun getTheme(): Flow<ThemeOptions> = dataStore.data.map {
        it[themeValuePreferenceKey]?.let { it1 -> ThemeOptions.fromValue(it1) } ?: ThemeOptions.Auto
    }

    override suspend fun setTheme(appTheme: ThemeOptions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val uiModeManager = ContextCompat.getSystemService(context, UiModeManager::class.java)
            uiModeManager?.setApplicationNightMode(
                when (appTheme) {
                    ThemeOptions.Light -> UiModeManager.MODE_NIGHT_NO
                    ThemeOptions.Dark -> UiModeManager.MODE_NIGHT_YES
                    ThemeOptions.Auto -> UiModeManager.MODE_NIGHT_AUTO
                }
            )
        } else {
            AppCompatDelegate.setDefaultNightMode(
                when (appTheme) {
                    ThemeOptions.Light -> AppCompatDelegate.MODE_NIGHT_NO
                    ThemeOptions.Dark -> AppCompatDelegate.MODE_NIGHT_YES
                    ThemeOptions.Auto -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
                }
            )
        }
        dataStore.edit {
            it[themeValuePreferenceKey] = appTheme.value
        }
    }

    companion object {
        private val themeValuePreferenceKey = intPreferencesKey("themeValuePreferenceKey")
    }
}