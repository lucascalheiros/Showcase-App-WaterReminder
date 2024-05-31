package com.github.lucascalheiros.waterremindermvp.domain.userinformation

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import androidx.test.platform.app.InstrumentationRegistry
import org.koin.core.qualifier.named

val Context.dataStore by preferencesDataStore(name = "TEST_PREFERENCES")

val context: Context
    get() = InstrumentationRegistry.getInstrumentation().targetContext.applicationContext

val themeDataStoreQualifier = named("THEME_DATASTORE")