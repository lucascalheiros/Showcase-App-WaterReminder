package com.github.lucascalheiros.waterreminder.data.notificationprovider.di

import com.github.lucascalheiros.waterreminder.common.util.DispatchersQualifier
import com.github.lucascalheiros.waterreminder.data.notificationprovider.data.repositories.NotificationSchedulerRepositoryImpl
import com.github.lucascalheiros.waterreminder.data.notificationprovider.data.repositories.WeekDayNotificationStateRepositoryImpl
import com.github.lucascalheiros.waterreminder.data.notificationprovider.data.repositories.datasources.NotificationEnabledDataSource
import com.github.lucascalheiros.waterreminder.data.notificationprovider.data.repositories.datasources.NotificationSchedulerDataSource
import com.github.lucascalheiros.waterreminder.data.notificationprovider.data.repositories.datasources.NotificationWeekDaysDataSource
import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.repositories.NotificationSchedulerRepository
import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.repositories.WeekDayNotificationStateRepository
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module


private val repositoryModule = module {
    singleOf(::NotificationSchedulerRepositoryImpl) bind NotificationSchedulerRepository::class
    singleOf(::WeekDayNotificationStateRepositoryImpl) bind WeekDayNotificationStateRepository::class
}

internal expect fun platformNotificationProviderModule(): Module

private val dataSourceModule = module {
    single {
        NotificationEnabledDataSource(
            get(notificationDataStore),
        )
    }
    single {
        NotificationSchedulerDataSource(
            get(notificationDataStore),
            get(),
        )
    }
    single {
        NotificationWeekDaysDataSource(
            get(notificationDataStore),
        )
    }
}

val notificationProviderModule = dataSourceModule + repositoryModule + platformNotificationProviderModule()