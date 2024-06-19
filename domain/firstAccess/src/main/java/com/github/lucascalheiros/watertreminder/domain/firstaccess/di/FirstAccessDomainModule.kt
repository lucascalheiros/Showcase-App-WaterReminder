package com.github.lucascalheiros.watertreminder.domain.firstaccess.di

import com.github.lucascalheiros.watertreminder.domain.firstaccess.domain.usecases.*
import com.github.lucascalheiros.watertreminder.domain.firstaccess.domain.usecases.impl.*
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val firstAccessDomainModule = module {
    singleOf(::CompleteFirstAccessFlowUseCaseImpl) bind CompleteFirstAccessFlowUseCase::class
    singleOf(::SetFirstDailyIntakeUseCaseImpl) bind SetFirstDailyIntakeUseCase::class
    singleOf(::IsFirstAccessCompletedUseCaseImpl) bind IsFirstAccessCompletedUseCase::class
    singleOf(::IsDailyIntakeFirstSetUseCaseImpl) bind IsDailyIntakeFirstSetUseCase::class
    singleOf(::GetFirstAccessNotificationDataUseCaseImpl) bind GetFirstAccessNotificationDataUseCase::class
    singleOf(::SetFirstAccessNotificationStateUseCaseImpl) bind SetFirstAccessNotificationStateUseCase::class
    singleOf(::SetStartNotificationTimeUseCaseImpl) bind SetStartNotificationTimeUseCase::class
    singleOf(::SetStopNotificationTimeUseCaseImpl) bind SetStopNotificationTimeUseCase::class
    singleOf(::SetNotificationFrequencyTimeUseCaseImpl) bind SetNotificationFrequencyTimeUseCase::class
} + firstAccessRepositoryModule