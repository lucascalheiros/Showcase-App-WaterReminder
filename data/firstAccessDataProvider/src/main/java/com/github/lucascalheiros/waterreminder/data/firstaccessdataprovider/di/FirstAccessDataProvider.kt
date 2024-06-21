package com.github.lucascalheiros.waterreminder.data.firstaccessdataprovider.di

import android.content.Context
import com.github.lucascalheiros.waterreminder.data.firstaccessdataprovider.data.repositories.FirstAccessNotificationDataRepositoryImpl
import com.github.lucascalheiros.waterreminder.data.firstaccessdataprovider.data.repositories.FirstUseFlagsRepositoryImpl
import com.github.lucascalheiros.waterreminder.data.firstaccessdataprovider.data.repositories.datasources.FirstAccessNotificationDataDao
import com.github.lucascalheiros.waterreminder.data.firstaccessdataprovider.data.repositories.datasources.FirstUseFlagsDao
import com.github.lucascalheiros.waterreminder.data.firstaccessdataprovider.data.repositories.datasources.datastore.dataStore
import com.github.lucascalheiros.waterreminder.domain.firstaccess.domain.repositories.FirstAccessNotificationDataRepository
import com.github.lucascalheiros.waterreminder.domain.firstaccess.domain.repositories.FirstUseFlagsRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind

import org.koin.dsl.module

private val repositoryModule = module {
    singleOf(::FirstUseFlagsRepositoryImpl) bind FirstUseFlagsRepository::class
    singleOf(::FirstAccessNotificationDataRepositoryImpl) bind FirstAccessNotificationDataRepository::class
}

private val datasourceModule = module {
    single(firstAccessDatastore) { get<Context>().dataStore }
    single {
        FirstUseFlagsDao(
            get(firstAccessDatastore),
        )
    }
    single {
        FirstAccessNotificationDataDao(
            get(firstAccessDatastore),
        )
    }
}

val firstAccessDataProviderModule = repositoryModule + datasourceModule
