package com.github.lucascalheiros.waterremindermvp.domain.remindnotifications.di

import com.github.lucascalheiros.waterremindermvp.domain.remindnotifications.domain.repositories.*
import com.github.lucascalheiros.waterremindermvp.domain.remindnotifications.data.repositories.*
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

internal val repositoryModule = module {
    singleOf(::NotificationSchedulerRepositoryImpl) bind NotificationSchedulerRepository::class
    singleOf(::WeekDayNotificationStateRepositoryImpl) bind WeekDayNotificationStateRepository::class
}