package com.github.lucascalheiros.waterreminder.data.themewrapper.di


import com.github.lucascalheiros.waterreminder.data.themewrapper.data.models.ThemeOptions
import com.github.lucascalheiros.waterreminder.data.themewrapper.data.repositories.datasources.ThemeWrapper
import platform.UIKit.UIApplication
import platform.UIKit.UIUserInterfaceStyle

internal class ThemeWrapperImpl(): ThemeWrapper {
    override fun setTheme(appTheme: ThemeOptions) {
        UIApplication.sharedApplication.keyWindow?.overrideUserInterfaceStyle = when (appTheme) {
            ThemeOptions.Light -> UIUserInterfaceStyle.UIUserInterfaceStyleLight
            ThemeOptions.Dark -> UIUserInterfaceStyle.UIUserInterfaceStyleDark
            ThemeOptions.Auto -> UIUserInterfaceStyle.UIUserInterfaceStyleUnspecified
        }
    }
}