package com.github.lucascalheiros.waterremindermvp.domain.userinformation.domain.usecases

import com.github.lucascalheiros.waterremindermvp.domain.userinformation.domain.models.AppTheme
import kotlinx.coroutines.flow.Flow

interface GetThemeUseCase {
    operator fun invoke(): Flow<AppTheme>
}