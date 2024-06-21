package com.github.lucascalheiros.waterreminder.data

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import org.koin.core.qualifier.named

val Context.dataStore by preferencesDataStore(name = "TEST_PREFERENCES")

val themeDataStoreQualifier = named("THEME_DATASTORE")