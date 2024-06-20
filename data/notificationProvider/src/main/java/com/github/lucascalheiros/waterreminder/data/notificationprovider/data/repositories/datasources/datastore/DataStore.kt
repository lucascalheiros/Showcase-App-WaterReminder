package com.github.lucascalheiros.waterreminder.data.notificationprovider.data.repositories.datasources.datastore

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore

private const val NOTIFICATION_PREFERENCES =
    "com.github.lucascalheiros.waterreminder.data.notificationprovider.datastore"

internal val Context.dataStore by preferencesDataStore(name = NOTIFICATION_PREFERENCES)