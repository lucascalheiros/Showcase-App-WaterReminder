package com.github.lucascalheiros.waterremindermvp.domain.userinformation.data.converters

import com.github.lucascalheiros.waterremindermvp.data.themewrapper.models.ThemeOptions
import com.github.lucascalheiros.waterremindermvp.domain.userinformation.domain.models.AppTheme

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