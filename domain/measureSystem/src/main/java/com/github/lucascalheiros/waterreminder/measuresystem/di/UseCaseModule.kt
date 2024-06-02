package com.github.lucascalheiros.waterreminder.measuresystem.di

import com.github.lucascalheiros.waterreminder.measuresystem.domain.usecases.*
import com.github.lucascalheiros.waterreminder.measuresystem.domain.usecases.impl.*
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

internal val useCaseModule = module {
    singleOf(::GetCurrentMeasureSystemUnitUseCaseImpl) bind GetCurrentMeasureSystemUnitUseCase::class
    singleOf(::RegisterCurrentMeasureSystemUnitUseCaseImpl) bind RegisterCurrentMeasureSystemUnitUseCase::class
}