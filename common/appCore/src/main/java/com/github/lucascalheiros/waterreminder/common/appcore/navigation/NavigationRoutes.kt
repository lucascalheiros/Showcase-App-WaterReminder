package com.github.lucascalheiros.waterreminder.common.appcore.navigation

import android.net.Uri
import androidx.core.net.toUri

sealed class NavigationRoutes(val uri: Uri) {
    data object MainFlow: NavigationRoutes(
        "android-app://com.github.lucascalheiros.waterreminder/mainFlowNav".toUri()
    )
}