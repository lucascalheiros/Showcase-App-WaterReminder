package com.github.lucascalheiros.waterreminder.data.themewrapper.data.converters

import com.github.lucascalheiros.waterreminder.data.themewrapper.data.models.ThemeOptions
import com.github.lucascalheiros.waterreminder.domain.userinformation.domain.models.AppTheme

internal fun ThemeOptions.toAppTheme(): AppTheme {
    return when (this) {
        ThemeOptions.Light -> AppTheme.Light
        ThemeOptions.Dark -> AppTheme.Dark
        ThemeOptions.Auto -> AppTheme.Auto
    }
}

internal fun AppTheme.toThemeOptions(): ThemeOptions {
    return when (this) {
        AppTheme.Light -> ThemeOptions.Light
        AppTheme.Dark -> ThemeOptions.Dark
        AppTheme.Auto -> ThemeOptions.Auto
    }
}