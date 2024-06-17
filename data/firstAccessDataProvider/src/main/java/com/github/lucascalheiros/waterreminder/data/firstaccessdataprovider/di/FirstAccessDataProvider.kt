package com.github.lucascalheiros.waterreminder.data.firstaccessdataprovider.di

import android.content.Context
import com.github.lucascalheiros.waterreminder.data.firstaccessdataprovider.data.FirstAccessNotificationDataDao
import com.github.lucascalheiros.waterreminder.data.firstaccessdataprovider.data.FirstUseFlagsDao
import com.github.lucascalheiros.waterreminder.data.firstaccessdataprovider.data.datastore.dataStore

import org.koin.dsl.module

val firstAccessDataProviderModule = module {
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