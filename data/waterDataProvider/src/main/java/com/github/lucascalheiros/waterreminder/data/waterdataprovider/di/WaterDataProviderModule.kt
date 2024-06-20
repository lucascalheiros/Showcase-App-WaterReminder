package com.github.lucascalheiros.waterreminder.data.waterdataprovider.di

import android.content.Context
import com.github.lucascalheiros.waterreminder.data.waterdataprovider.data.repositories.datasources.WaterDataDB
import com.github.lucascalheiros.waterreminder.data.waterdataprovider.data.repositories.datasources.WaterDataDB.Companion.db
import org.koin.dsl.module


import com.github.lucascalheiros.waterreminder.common.util.DispatchersQualifier
import com.github.lucascalheiros.waterreminder.data.waterdataprovider.data.repositories.ConsumedWaterRepositoryImpl
import com.github.lucascalheiros.waterreminder.data.waterdataprovider.data.repositories.DailyWaterConsumptionRepositoryImpl
import com.github.lucascalheiros.waterreminder.data.waterdataprovider.data.repositories.WaterSourceRepositoryImpl
import com.github.lucascalheiros.waterreminder.data.waterdataprovider.data.repositories.WaterSourceTypeRepositoryImpl
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.repositories.*
import kotlinx.coroutines.CoroutineDispatcher
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

private val repositoryModule = module {
    singleOf(::ConsumedWaterRepositoryImpl) bind ConsumedWaterRepository::class
    single {
        DailyWaterConsumptionRepositoryImpl(get(), get<CoroutineDispatcher>(DispatchersQualifier.Io))
    } bind DailyWaterConsumptionRepository::class
    singleOf(::WaterSourceRepositoryImpl) bind WaterSourceRepository::class
    single {
        WaterSourceTypeRepositoryImpl(get(), get<CoroutineDispatcher>(DispatchersQualifier.Io))
    } bind WaterSourceTypeRepository::class
}

private val dataSourceModule = module {
    single { get<Context>().db }
    single { get<WaterDataDB>().consumedWaterDao() }
    single { get<WaterDataDB>().dailyWaterConsumptionDao() }
    single { get<WaterDataDB>().waterSourceDao() }
    single { get<WaterDataDB>().waterSourceTypoDao() }
}

val waterDataProviderModule = repositoryModule + dataSourceModule