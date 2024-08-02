package com.github.lucascalheiros.waterreminder.data.waterdataprovider.di

import com.github.lucascalheiros.waterreminder.data.waterdataprovider.data.repositories.datasources.createDriver
import org.koin.core.module.Module
import org.koin.dsl.module

internal actual fun waterDataDbModule(): Module = module {
    single(waterDataDbDriver) { createDriver(get()) }
}

