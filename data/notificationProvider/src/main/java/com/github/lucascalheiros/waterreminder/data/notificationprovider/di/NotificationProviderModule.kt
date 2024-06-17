package com.github.lucascalheiros.waterreminder.data.notificationprovider.di

import android.content.Context
import com.github.lucascalheiros.waterreminder.data.notificationprovider.data.*
import com.github.lucascalheiros.waterreminder.data.notificationprovider.data.datastore.dataStore
import com.github.lucascalheiros.waterreminder.data.notificationprovider.data.impl.*
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module


val notificationProviderModule = module {
    single(notificationDataStore) { get<Context>().dataStore }
    single {
        NotificationEnabledDataSourceImpl(
            get(notificationDataStore),
        )
    } bind NotificationEnabledDataSource::class
    single {
        NotificationSchedulerDataSourceImpl(
            get(notificationDataStore),
            get(),
        )
    } bind NotificationSchedulerDataSource::class
    single {
        NotificationWeekDaysDataSourceImpl(
            get(notificationDataStore),
        )
    } bind NotificationWeekDaysDataSource::class
    singleOf(::AlarmManagerWrapper)
}