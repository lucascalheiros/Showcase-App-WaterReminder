package com.github.lucascalheiros.waterreminder.data.userprofileprovider.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.github.lucascalheiros.waterreminder.data.userprofileprovider.data.repositories.datasources.datastore.createDataStore
import com.github.lucascalheiros.waterreminder.data.userprofileprovider.data.repositories.datasources.datastore.dataStoreFileName
import org.koin.core.module.Module
import org.koin.dsl.module

internal actual fun userProfileDataStoreModule(): Module =  module {
    single(dataStoreQualifier) { createDataStore(get()) }
}

private fun createDataStore(context: Context): DataStore<Preferences> = createDataStore(
    producePath = { context.filesDir.resolve(dataStoreFileName).absolutePath }
)