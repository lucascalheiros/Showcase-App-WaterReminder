package com.github.lucascalheiros.waterreminder.data.firstaccessdataprovider.data.datastore

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore

private const val FIRST_ACCESS_PREFERENCES = "com.github.lucascalheiros.waterreminder.data.firstaccessdataprovider.data.datastore"

internal val Context.dataStore by preferencesDataStore(name = FIRST_ACCESS_PREFERENCES)
