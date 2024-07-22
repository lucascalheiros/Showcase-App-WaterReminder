package com.github.lucascalheiros.waterreminder.data.notificationprovider.di

import android.content.Context
import com.github.lucascalheiros.waterreminder.common.util.DispatchersQualifier
import com.github.lucascalheiros.waterreminder.data.notificationprovider.data.repositories.NotificationSchedulerRepositoryImpl
import com.github.lucascalheiros.waterreminder.data.notificationprovider.data.repositories.WeekDayNotificationStateRepositoryImpl
import com.github.lucascalheiros.waterreminder.data.notificationprovider.framework.AlarmManagerWrapper
import com.github.lucascalheiros.waterreminder.data.notificationprovider.data.repositories.datasources.NotificationEnabledDataSource
import com.github.lucascalheiros.waterreminder.data.notificationprovider.data.repositories.datasources.NotificationSchedulerDataSource
import com.github.lucascalheiros.waterreminder.data.notificationprovider.data.repositories.datasources.NotificationWeekDaysDataSource
import com.github.lucascalheiros.waterreminder.data.notificationprovider.data.repositories.datasources.datastore.dataStore
import com.github.lucascalheiros.waterreminder.data.notificationprovider.notification.helpers.AlarmManagerNotificationSetupHelper
import com.github.lucascalheiros.waterreminder.data.notificationprovider.notification.helpers.PreventNotificationByWeekDayHelper
import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.repositories.NotificationSchedulerRepository
import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.repositories.WeekDayNotificationStateRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module


private val repositoryModule = module {
    singleOf(::NotificationSchedulerRepositoryImpl) bind NotificationSchedulerRepository::class
    singleOf(::WeekDayNotificationStateRepositoryImpl) bind WeekDayNotificationStateRepository::class
}

private val dataSourceModule = module {
    single(notificationDataStore) { get<Context>().dataStore }
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
    singleOf(::AlarmManagerWrapper)
    single {
        AlarmManagerNotificationSetupHelper(
            get(DispatchersQualifier.Io),
            get(),
            get(),
            get(),
        )
    }
    singleOf(::PreventNotificationByWeekDayHelper)
}

val notificationProviderModule = dataSourceModule + repositoryModule