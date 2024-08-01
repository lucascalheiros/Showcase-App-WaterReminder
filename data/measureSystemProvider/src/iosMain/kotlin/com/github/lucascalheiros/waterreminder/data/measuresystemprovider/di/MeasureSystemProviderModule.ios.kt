package com.github.lucascalheiros.waterreminder.data.measuresystemprovider.di

import com.github.lucascalheiros.waterreminder.common.util.datastore.createDataStore
import com.github.lucascalheiros.waterreminder.data.measuresystemprovider.data.repositories.datasources.datastore.dataStoreFileName
import org.koin.core.module.Module
import org.koin.dsl.module

internal actual fun measureSystemDataStoreModule(): Module = module {
    single(measureSystemDataStoreQualifier) { createDataStore(dataStoreFileName) }
}
