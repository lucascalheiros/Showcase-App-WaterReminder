package com.github.lucascalheiros.waterreminder.data.themewrapper.di

import android.content.Context
import com.github.lucascalheiros.waterreminder.data.themewrapper.data.ThemeWrapper
import com.github.lucascalheiros.waterreminder.data.themewrapper.data.datastore.dataStore
import com.github.lucascalheiros.waterreminder.data.themewrapper.data.impl.ThemeWrapperImpl
import org.koin.dsl.bind
import org.koin.dsl.module

val themeWrapperModule = module {
    single(themeDataStore) { get<Context>().dataStore }
    single {
        ThemeWrapperImpl(
            get(themeDataStore),
            get(),
        )
    } bind ThemeWrapper::class
}