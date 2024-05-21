package com.github.lucascalheiros.waterremindermvp.domain.userinformation.domain.repositories

import com.github.lucascalheiros.waterremindermvp.domain.userinformation.domain.models.AppTheme
import kotlinx.coroutines.flow.Flow

interface ThemeRepository {
    fun getTheme(): Flow<AppTheme>
    fun setTheme(appTheme: AppTheme)
}