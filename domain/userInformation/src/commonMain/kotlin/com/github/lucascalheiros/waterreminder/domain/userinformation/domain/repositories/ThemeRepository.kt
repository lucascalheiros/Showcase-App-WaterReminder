package com.github.lucascalheiros.waterreminder.domain.userinformation.domain.repositories

import com.github.lucascalheiros.waterreminder.domain.userinformation.domain.models.AppTheme
import kotlinx.coroutines.flow.Flow

interface ThemeRepository {
    fun getTheme(): Flow<AppTheme>
    suspend fun setTheme(appTheme: AppTheme)
}