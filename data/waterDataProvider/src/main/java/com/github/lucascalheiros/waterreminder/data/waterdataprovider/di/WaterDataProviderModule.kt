package com.github.lucascalheiros.waterreminder.data.waterdataprovider.di

import android.content.Context
import com.github.lucascalheiros.waterreminder.data.waterdataprovider.datasources.local.WaterDataDB
import com.github.lucascalheiros.waterreminder.data.waterdataprovider.datasources.local.WaterDataDB.Companion.db
import org.koin.dsl.module

val waterDataProviderModule = module {
    single { get<Context>().db }
    single { get<WaterDataDB>().consumedWaterDao() }
    single { get<WaterDataDB>().dailyWaterConsumptionDao() }
    single { get<WaterDataDB>().waterSourceDao() }
    single { get<WaterDataDB>().waterSourceTypoDao() }
}