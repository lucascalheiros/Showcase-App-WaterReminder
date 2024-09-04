package com.github.lucascalheiros.waterreminder.data.notificationprovider.di

import com.github.lucascalheiros.waterreminder.common.util.DispatchersQualifier
import com.github.lucascalheiros.waterreminder.data.notificationprovider.ReminderNotificationDatabase
import com.github.lucascalheiros.waterreminder.data.notificationprovider.data.repositories.NotificationSchedulerRepositoryImpl
import com.github.lucascalheiros.waterreminder.data.notificationprovider.data.repositories.datasources.NotificationEnabledDataSource
import com.github.lucascalheiros.waterreminder.data.notificationprovider.data.repositories.datasources.NotificationSchedulerDataSource
import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.repositories.NotificationSchedulerRepository
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module


private val repositoryModule = module {
    singleOf(::NotificationSchedulerRepositoryImpl) bind NotificationSchedulerRepository::class
}

internal expect fun platformNotificationProviderModule(): Module

private val dataSourceModule = module {
    single { ReminderNotificationDatabase(get(notificationDbDriver)) }
    single {
        NotificationEnabledDataSource(
            get(notificationDataStore),
        )
    }
    single {
        NotificationSchedulerDataSource(
            get(),
            get(),
            get(DispatchersQualifier.Io),
        )
    }
}

val notificationProviderModule = dataSourceModule + repositoryModule + platformNotificationProviderModule()