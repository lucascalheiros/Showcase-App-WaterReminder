package com.github.lucascalheiros.waterreminder.domain.userinformation.domain.usecases

import com.github.lucascalheiros.waterreminder.domain.userinformation.domain.models.AppTheme

interface SetThemeUseCase {
    suspend operator fun invoke(appTheme: AppTheme)
}