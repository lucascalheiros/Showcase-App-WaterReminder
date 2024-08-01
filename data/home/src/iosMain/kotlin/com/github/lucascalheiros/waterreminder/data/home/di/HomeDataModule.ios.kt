package com.github.lucascalheiros.waterreminder.data.home.di

import com.github.lucascalheiros.waterreminder.common.util.datastore.createDataStore
import com.github.lucascalheiros.waterreminder.data.home.data.repositories.datasources.datastore.dataStoreFileName
import org.koin.core.module.Module
import org.koin.dsl.module

internal actual fun homeDataStoreModule(): Module = module {
    single(dataStoreQualifier) { createDataStore(dataStoreFileName) }
}