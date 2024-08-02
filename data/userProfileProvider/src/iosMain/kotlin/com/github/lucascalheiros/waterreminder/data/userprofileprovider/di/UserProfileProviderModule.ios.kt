package com.github.lucascalheiros.waterreminder.data.userprofileprovider.di

import com.github.lucascalheiros.waterreminder.common.util.datastore.createDataStore
import com.github.lucascalheiros.waterreminder.data.userprofileprovider.data.repositories.datasources.datastore.dataStoreFileName
import org.koin.core.module.Module
import org.koin.dsl.module

internal actual fun userProfileDataStoreModule(): Module = module {
    single(dataStoreQualifier) { createDataStore(dataStoreFileName) }
}
