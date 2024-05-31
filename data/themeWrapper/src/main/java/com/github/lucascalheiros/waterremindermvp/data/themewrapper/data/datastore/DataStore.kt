package com.github.lucascalheiros.waterremindermvp.data.themewrapper.data.datastore

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore

private const val THEME_PREFERENCES = "com.github.lucascalheiros.waterremindermvp.data.themewrapper.datastore"

internal val Context.dataStore by preferencesDataStore(name = THEME_PREFERENCES)
