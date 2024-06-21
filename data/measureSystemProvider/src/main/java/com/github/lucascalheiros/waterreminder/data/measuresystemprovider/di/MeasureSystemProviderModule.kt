package com.github.lucascalheiros.waterreminder.data.measuresystemprovider.di

import android.content.Context
import com.github.lucascalheiros.waterreminder.data.measuresystemprovider.data.repositories.*
import com.github.lucascalheiros.waterreminder.data.measuresystemprovider.data.repositories.datasources.datastore.dataStore
import com.github.lucascalheiros.waterreminder.measuresystem.domain.repositories.*
import org.koin.dsl.bind
import org.koin.dsl.module

val measureSystemProviderModule = module {
    single(measureSystemDataStoreQualifier) { get<Context>().dataStore }
    single { VolumeUnitRepositoryImpl(get(measureSystemDataStoreQualifier)) } bind VolumeUnitRepository::class
    single { TemperatureUnitRepositoryImpl(get(measureSystemDataStoreQualifier)) } bind TemperatureUnitRepository::class
    single { WeightUnitRepositoryImpl(get(measureSystemDataStoreQualifier)) } bind WeightUnitRepository::class
}