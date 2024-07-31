package com.github.lucascalheiros.waterreminder.data.measuresystemprovider.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.github.lucascalheiros.waterreminder.data.measuresystemprovider.data.repositories.datasources.datastore.createDataStore
import com.github.lucascalheiros.waterreminder.data.measuresystemprovider.data.repositories.datasources.datastore.dataStoreFileName
import org.koin.core.module.Module
import org.koin.dsl.module

internal actual fun measureSystemDataStoreModule(): Module = module {
    single(measureSystemDataStoreQualifier) { createDataStore(get<Context>()) }
}

private fun createDataStore(context: Context): DataStore<Preferences> = createDataStore(
    producePath = { context.filesDir.resolve(dataStoreFileName).absolutePath }
)