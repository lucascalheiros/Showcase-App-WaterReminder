package com.github.lucascalheiros.waterremindermvp.domain.watermanagement.di

import com.github.lucascalheiros.waterremindermvp.domain.watermanagement.domain.repositories.*
import com.github.lucascalheiros.waterremindermvp.domain.watermanagement.data.repositories.*
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

internal val repositoryModule = module {
    singleOf(::ConsumedWaterRepositoryImpl) bind ConsumedWaterRepository::class
    singleOf(::DailyWaterConsumptionRepositoryImpl) bind DailyWaterConsumptionRepository::class
    singleOf(::WaterSourceRepositoryImpl) bind WaterSourceRepository::class
    singleOf(::WaterSourceTypeRepositoryImpl) bind WaterSourceTypeRepository::class
    singleOf(::MeasureSystemUnitRepositoryImpl) bind MeasureSystemUnitRepository::class
}