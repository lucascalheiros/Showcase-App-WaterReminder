package com.github.lucascalheiros.waterremindermvp.domain.remindnotifications.di

import com.github.lucascalheiros.waterremindermvp.domain.remindnotifications.domain.frameworks.NotificationProvider
import com.github.lucascalheiros.waterremindermvp.domain.remindnotifications.frameworks.NotificationProviderImpl
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

internal val frameworkModule = module {
    singleOf(::NotificationProviderImpl) bind NotificationProvider::class
}