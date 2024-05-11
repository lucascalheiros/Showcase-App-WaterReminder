package com.github.lucascalheiros.waterremindermvp.common.appcore.navigation

import android.net.Uri
import androidx.core.net.toUri

sealed class NavigationRoutes(val uri: Uri) {

    data object HomeScreen: NavigationRoutes(
        "android-app://com.github.lucascalheiros.waterremindermvp.feature.home/homeFragment".toUri()
    )
    data object HistoryScreen: NavigationRoutes(
        "android-app://com.github.lucascalheiros.waterremindermvp.feature.history/historyFragment".toUri()
    )
    data object SettingsScreen: NavigationRoutes(
        "android-app://com.github.lucascalheiros.waterremindermvp.feature.settings/settingsFragment".toUri()
    )
}