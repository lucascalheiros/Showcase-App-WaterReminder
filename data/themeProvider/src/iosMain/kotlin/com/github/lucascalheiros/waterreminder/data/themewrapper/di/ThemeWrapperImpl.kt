package com.github.lucascalheiros.waterreminder.data.themewrapper.di


import com.github.lucascalheiros.waterreminder.data.themewrapper.data.models.ThemeOptions
import com.github.lucascalheiros.waterreminder.data.themewrapper.data.repositories.datasources.ThemeWrapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import platform.UIKit.UIApplication
import platform.UIKit.UIUserInterfaceStyle
import platform.UIKit.UIWindowScene

internal class ThemeWrapperImpl(private val dispatcher: CoroutineDispatcher): ThemeWrapper {
    override suspend fun setTheme(appTheme: ThemeOptions) = withContext(dispatcher) {
        (UIApplication.sharedApplication.connectedScenes.firstOrNull() as? UIWindowScene)?.keyWindow?.overrideUserInterfaceStyle = when (appTheme) {
            ThemeOptions.Light -> UIUserInterfaceStyle.UIUserInterfaceStyleLight
            ThemeOptions.Dark -> UIUserInterfaceStyle.UIUserInterfaceStyleDark
            ThemeOptions.Auto -> UIUserInterfaceStyle.UIUserInterfaceStyleUnspecified
        }
    }
}