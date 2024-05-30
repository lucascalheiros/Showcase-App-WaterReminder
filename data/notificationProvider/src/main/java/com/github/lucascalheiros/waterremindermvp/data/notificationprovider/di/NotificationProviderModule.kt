package com.github.lucascalheiros.waterremindermvp.data.notificationprovider.di

import com.github.lucascalheiros.waterremindermvp.data.notificationprovider.data.*
import com.github.lucascalheiros.waterremindermvp.data.notificationprovider.data.impl.*
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val notificationProviderModule = module {
    singleOf(::NotificationEnabledDataSourceImpl) bind NotificationEnabledDataSource::class
    singleOf(::NotificationSchedulerWrapperDataSourceImpl) bind NotificationSchedulerWrapperDataSource::class
    singleOf(::NotificationWeekDaysDataSourceImpl) bind NotificationWeekDaysDataSource::class
}