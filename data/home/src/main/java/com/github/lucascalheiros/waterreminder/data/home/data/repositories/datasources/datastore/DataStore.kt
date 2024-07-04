package com.github.lucascalheiros.waterreminder.data.home.data.repositories.datasources.datastore

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore

private const val PREFERENCES = "com.github.lucascalheiros.waterreminder.data.home.data.repositories.datasources.datastore"

internal val Context.dataStore by preferencesDataStore(name = PREFERENCES)
