package com.github.lucascalheiros.waterremindermvp.domain.watermanagement.di

import com.github.lucascalheiros.waterremindermvp.domain.watermanagement.domain.usecases.*
import com.github.lucascalheiros.waterremindermvp.domain.watermanagement.domain.usecases.impl.*
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

internal val useCaseModule = module {
    singleOf(::CreateWaterSourceUseCaseImpl) bind CreateWaterSourceUseCase::class
    singleOf(::GetWaterSourceUseCaseImpl) bind GetWaterSourceUseCase::class
    singleOf(::GetWaterSourceTypeUseCaseImpl) bind GetWaterSourceTypeUseCase::class
    singleOf(::DeleteWaterSourceUseCaseImpl) bind DeleteWaterSourceUseCase::class
    singleOf(::GetConsumedWaterUseCaseImpl) bind GetConsumedWaterUseCase::class
    singleOf(::GetDailyWaterConsumptionUseCaseImpl) bind GetDailyWaterConsumptionUseCase::class
    singleOf(::SaveDailyWaterConsumptionUseCaseImpl) bind SaveDailyWaterConsumptionUseCase::class
    singleOf(::RegisterConsumedWaterUseCaseImpl) bind RegisterConsumedWaterUseCase::class
    singleOf(::GetDailyWaterConsumptionSummaryUseCaseImpl) bind GetDailyWaterConsumptionSummaryUseCase::class
    singleOf(::DeleteConsumedWaterUseCaseImpl) bind DeleteConsumedWaterUseCase::class
    singleOf(::GetCurrentMeasureSystemUnitUseCaseImpl) bind GetCurrentMeasureSystemUnitUseCase::class
    singleOf(::RegisterCurrentMeasureSystemUnitUseCaseImpl) bind RegisterCurrentMeasureSystemUnitUseCase::class
    singleOf(::GetDefaultAddWaterSourceInfoUseCaseImpl) bind GetDefaultAddWaterSourceInfoUseCase::class
}