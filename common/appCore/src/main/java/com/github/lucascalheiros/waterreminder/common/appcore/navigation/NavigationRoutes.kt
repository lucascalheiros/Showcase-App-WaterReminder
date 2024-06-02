package com.github.lucascalheiros.waterreminder.common.appcore.navigation

import android.net.Uri
import androidx.core.net.toUri

sealed class NavigationRoutes(val uri: Uri) {

    data object HomeScreen: NavigationRoutes(
        "android-app://com.github.lucascalheiros.waterreminder.feature.home/homeFragment".toUri()
    )
    data object HistoryScreen: NavigationRoutes(
        "android-app://com.github.lucascalheiros.waterreminder.feature.history/historyFragment".toUri()
    )
    data object SettingsScreen: NavigationRoutes(
        "android-app://com.github.lucascalheiros.waterreminder.feature.settings/settingsFragment".toUri()
    )
}