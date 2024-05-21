package com.github.lucascalheiros.waterremindermvp.data.themewrapper.framework

import android.app.UiModeManager
import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import com.github.lucascalheiros.waterremindermvp.data.themewrapper.models.ThemeOptions
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class ThemeWrapper(
    private val context: Context
) {

    private val theme = MutableStateFlow(currentTheme())

    fun getTheme(): Flow<ThemeOptions> = theme

    fun setTheme(appTheme: ThemeOptions) {
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
        theme.value = currentTheme()
    }


    private fun currentTheme(): ThemeOptions {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val uiModeManager = ContextCompat.getSystemService(context, UiModeManager::class.java)
            when (uiModeManager?.nightMode) {
                UiModeManager.MODE_NIGHT_NO -> ThemeOptions.Light
                UiModeManager.MODE_NIGHT_YES -> ThemeOptions.Dark
                else -> ThemeOptions.Auto
            }
        } else {
            when (AppCompatDelegate.getDefaultNightMode()) {
                AppCompatDelegate.MODE_NIGHT_NO -> ThemeOptions.Light
                AppCompatDelegate.MODE_NIGHT_YES -> ThemeOptions.Dark
                else -> ThemeOptions.Auto
            }
        }
    }
}