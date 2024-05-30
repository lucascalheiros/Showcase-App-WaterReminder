package com.github.lucascalheiros.waterremindermvp.data.notificationprovider.data.datastore

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore

private const val NOTIFICATION_PREFERENCES =
    "com.github.lucascalheiros.waterremindermvp.data.notificationprovider.datastore"

internal val Context.dataStore by preferencesDataStore(name = NOTIFICATION_PREFERENCES)