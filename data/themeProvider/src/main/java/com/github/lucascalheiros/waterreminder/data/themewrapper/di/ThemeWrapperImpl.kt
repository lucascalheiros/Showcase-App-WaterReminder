package com.github.lucascalheiros.waterreminder.data.themewrapper.di

import android.app.UiModeManager
import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import com.github.lucascalheiros.waterreminder.data.themewrapper.data.models.ThemeOptions
import com.github.lucascalheiros.waterreminder.data.themewrapper.data.repositories.datasources.ThemeWrapper

internal class ThemeWrapperImpl(private val context: Context): ThemeWrapper {
    override fun setTheme(appTheme: ThemeOptions) {
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
    }
}