package com.github.lucascalheiros.waterreminder.measuresystem.di
import com.github.lucascalheiros.waterreminder.measuresystem.domain.usecases.GetTemperatureUnitUseCase
import com.github.lucascalheiros.waterreminder.measuresystem.domain.usecases.GetVolumeUnitUseCase
import com.github.lucascalheiros.waterreminder.measuresystem.domain.usecases.GetWeightUnitUseCase
import com.github.lucascalheiros.waterreminder.measuresystem.domain.usecases.SetTemperatureUnitUseCase
import com.github.lucascalheiros.waterreminder.measuresystem.domain.usecases.SetVolumeUnitUseCase
import com.github.lucascalheiros.waterreminder.measuresystem.domain.usecases.SetWeightUnitUseCase
import com.github.lucascalheiros.waterreminder.measuresystem.domain.usecases.impl.GetTemperatureUnitUseCaseImpl
import com.github.lucascalheiros.waterreminder.measuresystem.domain.usecases.impl.GetVolumeUnitUseCaseImpl
import com.github.lucascalheiros.waterreminder.measuresystem.domain.usecases.impl.GetWeightUnitUseCaseImpl
import com.github.lucascalheiros.waterreminder.measuresystem.domain.usecases.impl.SetTemperatureUnitUseCaseImpl
import com.github.lucascalheiros.waterreminder.measuresystem.domain.usecases.impl.SetVolumeUnitUseCaseImpl
import com.github.lucascalheiros.waterreminder.measuresystem.domain.usecases.impl.SetWeightUnitUseCaseImpl
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val domainMeasureSystemModule =  module {
    singleOf(::GetVolumeUnitUseCaseImpl) bind GetVolumeUnitUseCase::class
    singleOf(::GetTemperatureUnitUseCaseImpl) bind GetTemperatureUnitUseCase::class
    singleOf(::GetWeightUnitUseCaseImpl) bind GetWeightUnitUseCase::class
    singleOf(::SetVolumeUnitUseCaseImpl) bind SetVolumeUnitUseCase::class
    singleOf(::SetTemperatureUnitUseCaseImpl) bind SetTemperatureUnitUseCase::class
    singleOf(::SetWeightUnitUseCaseImpl) bind SetWeightUnitUseCase::class
}