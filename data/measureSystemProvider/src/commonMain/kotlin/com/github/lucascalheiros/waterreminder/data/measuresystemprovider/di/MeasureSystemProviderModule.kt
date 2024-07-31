package com.github.lucascalheiros.waterreminder.data.measuresystemprovider.di

import com.github.lucascalheiros.waterreminder.data.measuresystemprovider.data.repositories.TemperatureUnitRepositoryImpl
import com.github.lucascalheiros.waterreminder.data.measuresystemprovider.data.repositories.VolumeUnitRepositoryImpl
import com.github.lucascalheiros.waterreminder.data.measuresystemprovider.data.repositories.WeightUnitRepositoryImpl
import com.github.lucascalheiros.waterreminder.measuresystem.domain.repositories.TemperatureUnitRepository
import com.github.lucascalheiros.waterreminder.measuresystem.domain.repositories.VolumeUnitRepository
import com.github.lucascalheiros.waterreminder.measuresystem.domain.repositories.WeightUnitRepository
import org.koin.core.module.Module
import org.koin.dsl.bind
import org.koin.dsl.module

internal expect fun measureSystemDataStoreModule(): Module

val measureSystemProviderModule = module {
    single { VolumeUnitRepositoryImpl(get(measureSystemDataStoreQualifier)) } bind VolumeUnitRepository::class
    single { TemperatureUnitRepositoryImpl(get(measureSystemDataStoreQualifier)) } bind TemperatureUnitRepository::class
    single { WeightUnitRepositoryImpl(get(measureSystemDataStoreQualifier)) } bind WeightUnitRepository::class
} + measureSystemDataStoreModule()