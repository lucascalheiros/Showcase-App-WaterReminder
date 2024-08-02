package com.github.lucascalheiros.waterreminder.domain.userinformation.domain.usecases

import com.github.lucascalheiros.waterreminder.domain.userinformation.domain.models.AppTheme
import kotlinx.coroutines.flow.Flow

interface GetThemeUseCase {
    operator fun invoke(): Flow<AppTheme>
}