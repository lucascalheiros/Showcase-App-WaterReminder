package com.github.lucascalheiros.waterremindermvp.data.themewrapper.di

import android.content.Context
import com.github.lucascalheiros.waterremindermvp.data.themewrapper.data.ThemeWrapper
import com.github.lucascalheiros.waterremindermvp.data.themewrapper.data.datastore.dataStore
import org.koin.dsl.module

val themeWrapperModule = module {
    single(themeDataStore) { get<Context>().dataStore }
    single {
        ThemeWrapper(
            get(themeDataStore),
            get(),
        )
    }
}