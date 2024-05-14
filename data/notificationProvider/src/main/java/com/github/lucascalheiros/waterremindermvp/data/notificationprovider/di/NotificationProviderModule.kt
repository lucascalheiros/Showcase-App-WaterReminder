package com.github.lucascalheiros.waterremindermvp.data.notificationprovider.di

import com.github.lucascalheiros.waterremindermvp.data.notificationprovider.framework.NotificationProviderWrapper
import com.github.lucascalheiros.waterremindermvp.data.notificationprovider.framework.impl.NotificationProviderWrapperImpl
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val notificationProviderModule = module {
    singleOf(::NotificationProviderWrapperImpl) bind NotificationProviderWrapper::class
}