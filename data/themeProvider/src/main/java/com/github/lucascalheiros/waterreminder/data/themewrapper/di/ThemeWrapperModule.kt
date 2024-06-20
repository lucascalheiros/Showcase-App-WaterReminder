package com.github.lucascalheiros.waterreminder.data.themewrapper.di

import android.content.Context
import com.github.lucascalheiros.waterreminder.data.themewrapper.data.repositories.ThemeRepositoryImpl
import com.github.lucascalheiros.waterreminder.data.themewrapper.data.repositories.datasources.ThemeWrapper
import com.github.lucascalheiros.waterreminder.data.themewrapper.data.repositories.datasources.datastore.dataStore
import com.github.lucascalheiros.waterreminder.domain.userinformation.domain.repositories.ThemeRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

private val repositoryModule = module {
    singleOf(::ThemeRepositoryImpl) bind ThemeRepository::class
}

private val datasourceModule = module {
    single(themeDataStore) { get<Context>().dataStore }
    single {
        ThemeWrapper(
            get(themeDataStore),
            get(),
        )
    }
}

val themeDataProviderModule = repositoryModule + datasourceModule