package com.github.lucascalheiros.waterreminder.domain.userinformation.di


import com.github.lucascalheiros.waterreminder.domain.userinformation.domain.usecases.*
import com.github.lucascalheiros.waterreminder.domain.userinformation.domain.usecases.impl.*
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

internal val useCaseModule = module {
    singleOf(::GetThemeUseCaseImpl) bind GetThemeUseCase::class
    singleOf(::SetThemeUseCaseImpl) bind SetThemeUseCase::class
    singleOf(::GetUserProfileUseCaseImpl) bind GetUserProfileUseCase::class
    singleOf(::SetUserProfileUseCaseImpl) bind SetUserProfileUseCase::class
    singleOf(::GetCalculatedIntakeUseCaseImpl) bind GetCalculatedIntakeUseCase::class
    singleOf(::SetUserProfileNameUseCaseImpl) bind SetUserProfileNameUseCase::class
    singleOf(::SetUserProfileWeightUseCaseImpl) bind SetUserProfileWeightUseCase::class
    singleOf(::SetUserProfileTemperatureLevelUseCaseImpl) bind SetUserProfileTemperatureLevelUseCase::class
    singleOf(::SetUserProfileActivityLevelUseCaseImpl) bind SetUserProfileActivityLevelUseCase::class

}