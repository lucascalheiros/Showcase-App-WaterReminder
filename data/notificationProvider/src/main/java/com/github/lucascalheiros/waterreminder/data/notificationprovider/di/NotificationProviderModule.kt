package com.github.lucascalheiros.waterreminder.data.notificationprovider.di

import android.content.Context
import com.github.lucascalheiros.waterreminder.data.notificationprovider.data.NotificationEnabledDataSource
import com.github.lucascalheiros.waterreminder.data.notificationprovider.data.NotificationSchedulerWrapperDataSource
import com.github.lucascalheiros.waterreminder.data.notificationprovider.data.NotificationWeekDaysDataSource
import com.github.lucascalheiros.waterreminder.data.notificationprovider.data.datastore.dataStore
import com.github.lucascalheiros.waterreminder.data.notificationprovider.data.impl.NotificationEnabledDataSourceImpl
import com.github.lucascalheiros.waterreminder.data.notificationprovider.data.impl.NotificationSchedulerWrapperDataSourceImpl
import com.github.lucascalheiros.waterreminder.data.notificationprovider.data.impl.NotificationWeekDaysDataSourceImpl
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
        NotificationSchedulerWrapperDataSourceImpl(
            get(notificationDataStore),
            get(),
        )
    } bind NotificationSchedulerWrapperDataSource::class
    single {
        NotificationWeekDaysDataSourceImpl(
            get(notificationDataStore),
        )
    } bind NotificationWeekDaysDataSource::class
}