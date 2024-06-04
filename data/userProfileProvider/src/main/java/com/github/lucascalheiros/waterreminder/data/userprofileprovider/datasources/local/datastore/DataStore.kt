package com.github.lucascalheiros.waterreminder.data.userprofileprovider.datasources.local.datastore

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore

private const val USERPROFILE_PREFERENCES = "com.github.lucascalheiros.waterreminder.data.userProfileProvider.datastore"

internal val Context.dataStore by preferencesDataStore(name = USERPROFILE_PREFERENCES)
