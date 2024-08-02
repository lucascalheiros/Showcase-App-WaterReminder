package com.github.lucascalheiros.waterreminder.data.firstaccessdataprovider.di

import com.github.lucascalheiros.waterreminder.common.util.datastore.createDataStore
import com.github.lucascalheiros.waterreminder.data.firstaccessdataprovider.data.repositories.datasources.datastore.dataStoreFileName
import org.koin.core.module.Module
import org.koin.dsl.module

internal actual fun firstAccessDataStoreModule(): Module = module {
    single(firstAccessDatastore) { createDataStore(dataStoreFileName) }
}
