package com.github.lucascalheiros.waterreminder.common.ui

import android.content.Context
import android.content.res.Configuration
import androidx.annotation.ColorInt
import androidx.annotation.ColorLong

fun Context.isDarkThemeEnabled(): Boolean {
    return resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES
}

fun Context.getThemeAwareColor(@ColorLong lightColor: Long, @ColorLong darkColor: Long): Long {
    return if (isDarkThemeEnabled()) {
        darkColor
    } else {
        lightColor
    }
}

fun Context.getThemeAwareColor(@ColorInt lightColor: Int, @ColorInt darkColor: Int): Int {
    return if (isDarkThemeEnabled()) {
        darkColor
    } else {
        lightColor
    }
}