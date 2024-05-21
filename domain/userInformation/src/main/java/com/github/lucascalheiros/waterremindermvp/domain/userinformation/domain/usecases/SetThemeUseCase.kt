package com.github.lucascalheiros.waterremindermvp.domain.userinformation.domain.usecases

import com.github.lucascalheiros.waterremindermvp.domain.userinformation.domain.models.AppTheme

interface SetThemeUseCase {
    suspend operator fun invoke(appTheme: AppTheme)
}