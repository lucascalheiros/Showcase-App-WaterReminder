package com.github.lucascalheiros.waterreminder.data.measuresystemprovider

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import org.koin.core.qualifier.named

val Context.dataStore by preferencesDataStore(name = "TEST_PREFERENCES")

val measureSystemDataStoreQualifier = named("MEASURE_SYSTEM_DATASTORE")