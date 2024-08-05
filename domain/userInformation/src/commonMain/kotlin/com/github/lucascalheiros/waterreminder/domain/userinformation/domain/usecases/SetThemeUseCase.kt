package com.github.lucascalheiros.waterreminder.domain.userinformation.domain.usecases

import com.github.lucascalheiros.waterreminder.domain.userinformation.domain.models.AppTheme
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines

interface SetThemeUseCase {
    @NativeCoroutines
    suspend operator fun invoke(appTheme: AppTheme)
}