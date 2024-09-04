package com.github.lucascalheiros.waterreminder.data.themewrapper.data.repositories.datasources

import com.github.lucascalheiros.waterreminder.data.themewrapper.data.models.ThemeOptions

interface ThemeWrapper {
    suspend fun setTheme(appTheme: ThemeOptions)
}