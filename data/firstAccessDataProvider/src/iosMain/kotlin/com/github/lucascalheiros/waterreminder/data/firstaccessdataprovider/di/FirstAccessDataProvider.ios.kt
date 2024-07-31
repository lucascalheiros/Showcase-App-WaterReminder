package com.github.lucascalheiros.waterreminder.data.firstaccessdataprovider.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.github.lucascalheiros.waterreminder.data.firstaccessdataprovider.data.repositories.datasources.datastore.dataStoreFileName
import com.github.lucascalheiros.waterreminder.data.firstaccessdataprovider.data.repositories.datasources.datastore.createDataStore
import kotlinx.cinterop.ExperimentalForeignApi
import org.koin.core.module.Module
import org.koin.dsl.module
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSURL
import platform.Foundation.NSUserDomainMask

internal actual fun firstAccessDataStoreModule(): Module = module {
    single(firstAccessDatastore) { createDataStore() }
}

@OptIn(ExperimentalForeignApi::class)
private fun createDataStore(): DataStore<Preferences> = createDataStore(
    producePath = {
        val documentDirectory: NSURL? = NSFileManager.defaultManager.URLForDirectory(
            directory = NSDocumentDirectory,
            inDomain = NSUserDomainMask,
            appropriateForURL = null,
            create = false,
            error = null,
        )
        requireNotNull(documentDirectory).path + "/$dataStoreFileName"
    }
)
