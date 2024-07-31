package com.github.lucascalheiros.waterreminder.data.firstaccessdataprovider.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.github.lucascalheiros.waterreminder.data.firstaccessdataprovider.data.repositories.datasources.datastore.dataStoreFileName
import com.github.lucascalheiros.waterreminder.data.firstaccessdataprovider.data.repositories.datasources.datastore.createDataStore
import org.koin.core.module.Module
import org.koin.dsl.module

internal actual fun firstAccessDataStoreModule(): Module = module {
    single(firstAccessDatastore) { createDataStore(get<Context>()) }
}

private fun createDataStore(context: Context): DataStore<Preferences> = createDataStore(
    producePath = { context.filesDir.resolve(dataStoreFileName).absolutePath }
)