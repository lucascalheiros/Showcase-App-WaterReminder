package com.github.lucascalheiros.watertreminder.domain.firstaccess.di

import com.github.lucascalheiros.waterreminder.data.firstaccessdataprovider.di.firstAccessDataProviderModule
import com.github.lucascalheiros.watertreminder.domain.firstaccess.domain.repositories.*
import com.github.lucascalheiros.watertreminder.domain.firstaccess.data.*
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

internal val firstAccessRepositoryModule = module {
    singleOf(::FirstUseFlagsRepositoryImpl) bind FirstUseFlagsRepository::class
    singleOf(::FirstAccessNotificationDataRepositoryImpl) bind FirstAccessNotificationDataRepository::class
} + firstAccessDataProviderModule