package com.github.lucascalheiros.waterreminder.data.themewrapper.di

import com.github.lucascalheiros.waterreminder.data.themewrapper.data.repositories.ThemeRepositoryImpl
import com.github.lucascalheiros.waterreminder.data.themewrapper.data.repositories.datasources.ThemeDataSource
import com.github.lucascalheiros.waterreminder.domain.userinformation.domain.repositories.ThemeRepository
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

private val repositoryModule = module {
    singleOf(::ThemeRepositoryImpl) bind ThemeRepository::class
}

internal expect fun themeDataStoreModule(): Module

private val datasourceModule = module {
    single {
        ThemeDataSource(
            get(themeDataStore),
            get(),
        )
    }
}

val themeDataProviderModule = repositoryModule + datasourceModule + themeDataStoreModule()