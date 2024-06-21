package com.github.lucascalheiros.waterreminder.data.measuresystemprovider.data.repositories.datasources.datastore

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore

private const val PREFERENCES = "com.github.lucascalheiros.waterreminder.data.measuresystemprovider.data.datastore"

internal val Context.dataStore by preferencesDataStore(name = PREFERENCES)
