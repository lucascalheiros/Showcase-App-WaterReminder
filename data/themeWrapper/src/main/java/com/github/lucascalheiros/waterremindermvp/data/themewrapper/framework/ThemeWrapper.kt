package com.github.lucascalheiros.waterremindermvp.data.themewrapper.framework

import android.app.UiModeManager
import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.github.lucascalheiros.waterremindermvp.data.themewrapper.models.ThemeOptions
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ThemeWrapper(
    private val context: Context
) {

    private val Context.dataStore by preferencesDataStore(name = THEME_PREFERENCES)

    fun getTheme(): Flow<ThemeOptions> = context.dataStore.data.map {
        it[themeValuePreferenceKey]?.let { it1 -> ThemeOptions.fromValue(it1) } ?: ThemeOptions.Auto
    }

    suspend fun setTheme(appTheme: ThemeOptions) {
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
        context.dataStore.edit {
            it[themeValuePreferenceKey] = appTheme.value
        }
    }

    companion object {
        private const val THEME_PREFERENCES = "com.github.lucascalheiros.waterremindermvp.data.themewrapper.datastore"
        private val themeValuePreferenceKey  = intPreferencesKey("themeValuePreferenceKey")

    }
}