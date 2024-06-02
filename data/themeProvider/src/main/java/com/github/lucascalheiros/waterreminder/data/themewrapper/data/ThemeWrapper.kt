package com.github.lucascalheiros.waterreminder.data.themewrapper.data

import com.github.lucascalheiros.waterreminder.data.themewrapper.models.ThemeOptions
import kotlinx.coroutines.flow.Flow

interface ThemeWrapper {
    fun getTheme(): Flow<ThemeOptions>
    suspend fun setTheme(appTheme: ThemeOptions)
}