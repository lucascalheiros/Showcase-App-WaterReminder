package com.github.lucascalheiros.waterreminder.domain.userinformation.domain.usecases.impl

import com.github.lucascalheiros.waterreminder.domain.userinformation.domain.repositories.ThemeRepository
import com.github.lucascalheiros.waterreminder.domain.userinformation.domain.models.AppTheme
import com.github.lucascalheiros.waterreminder.domain.userinformation.domain.usecases.SetThemeUseCase

class SetThemeUseCaseImpl(
    private val themeRepository: ThemeRepository
): SetThemeUseCase {
    override suspend fun invoke(appTheme: AppTheme) {
        themeRepository.setTheme(appTheme)
    }
}