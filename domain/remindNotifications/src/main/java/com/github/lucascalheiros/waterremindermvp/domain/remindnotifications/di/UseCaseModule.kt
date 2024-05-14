package com.github.lucascalheiros.waterremindermvp.domain.remindnotifications.di

import com.github.lucascalheiros.waterremindermvp.domain.remindnotifications.domain.usecases.*
import com.github.lucascalheiros.waterremindermvp.domain.remindnotifications.domain.usecases.impl.*
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

internal val useCaseModule = module {
    singleOf(::DeleteScheduledNotificationUseCaseImpl) bind DeleteScheduledNotificationUseCase::class
    singleOf(::GetScheduledNotificationsUseCaseImpl) bind GetScheduledNotificationsUseCase::class
    singleOf(::ScheduleNotificationUseCaseImpl) bind ScheduleNotificationUseCase::class
    singleOf(::SetupRemindNotificationsUseCaseImpl) bind SetupRemindNotificationsUseCase::class
}