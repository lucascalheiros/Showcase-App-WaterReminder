package com.github.lucascalheiros.waterreminder.data.themewrapper.di

import com.github.lucascalheiros.waterreminder.common.util.datastore.createDataStore
import com.github.lucascalheiros.waterreminder.data.themewrapper.data.repositories.datasources.ThemeWrapper
import com.github.lucascalheiros.waterreminder.data.themewrapper.data.repositories.datasources.datastore.dataStoreFileName
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

internal actual fun themeDataStoreModule(): Module =  module {
    single(themeDataStore) { createDataStore(dataStoreFileName) }
    singleOf(::ThemeWrapperImpl) bind ThemeWrapper::class
}
