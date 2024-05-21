package com.github.lucascalheiros.waterremindermvp.domain.userinformation.domain.usecases.impl

import com.github.lucascalheiros.waterremindermvp.domain.userinformation.domain.repositories.ThemeRepository
import com.github.lucascalheiros.waterremindermvp.domain.userinformation.domain.models.AppTheme
import com.github.lucascalheiros.waterremindermvp.domain.userinformation.domain.usecases.GetThemeUseCase
import kotlinx.coroutines.flow.Flow

class GetThemeUseCaseImpl(
    private val themeRepository: ThemeRepository
): GetThemeUseCase {
    override fun invoke(): Flow<AppTheme> {
        return themeRepository.getTheme()
    }
}