package com.github.lucascalheiros.waterreminder.measuresystem.di

import com.github.lucascalheiros.waterreminder.measuresystem.domain.usecases.*
import com.github.lucascalheiros.waterreminder.measuresystem.domain.usecases.impl.*
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

internal val useCaseModule = module {
    singleOf(::GetVolumeUnitUseCaseImpl) bind GetVolumeUnitUseCase::class
    singleOf(::GetTemperatureUnitUseCaseImpl) bind GetTemperatureUnitUseCase::class
    singleOf(::GetWeightUnitUseCaseImpl) bind GetWeightUnitUseCase::class
    singleOf(::SetVolumeUnitUseCaseImpl) bind SetVolumeUnitUseCase::class
    singleOf(::SetTemperatureUnitUseCaseImpl) bind SetTemperatureUnitUseCase::class
    singleOf(::SetWeightUnitUseCaseImpl) bind SetWeightUnitUseCase::class
}