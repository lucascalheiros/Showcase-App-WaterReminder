package com.github.lucascalheiros.waterreminder.domain.userinformation

import com.github.lucascalheiros.waterreminder.domain.userinformation.domain.usecases.*
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

class UserInformationInjector: KoinComponent {
    fun getThemeUseCase(): GetThemeUseCase = get()
    fun setThemeUseCase(): SetThemeUseCase = get()
    fun getUserProfileUseCase(): GetUserProfileUseCase = get()
    fun setUserProfileUseCase(): SetUserProfileUseCase = get()
    fun getCalculatedIntakeUseCase(): GetCalculatedIntakeUseCase = get()
    fun setUserProfileNameUseCase(): SetUserProfileNameUseCase = get()
    fun setUserProfileWeightUseCase(): SetUserProfileWeightUseCase = get()
    fun setUserProfileTemperatureLevelUseCase(): SetUserProfileTemperatureLevelUseCase = get()
    fun setUserProfileActivityLevelUseCase(): SetUserProfileActivityLevelUseCase = get()
}