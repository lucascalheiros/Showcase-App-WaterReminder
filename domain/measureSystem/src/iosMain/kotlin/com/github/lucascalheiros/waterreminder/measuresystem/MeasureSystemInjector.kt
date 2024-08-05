package com.github.lucascalheiros.waterreminder.measuresystem

import com.github.lucascalheiros.waterreminder.measuresystem.domain.usecases.GetTemperatureUnitUseCase
import com.github.lucascalheiros.waterreminder.measuresystem.domain.usecases.GetVolumeUnitUseCase
import com.github.lucascalheiros.waterreminder.measuresystem.domain.usecases.GetWeightUnitUseCase
import com.github.lucascalheiros.waterreminder.measuresystem.domain.usecases.SetTemperatureUnitUseCase
import com.github.lucascalheiros.waterreminder.measuresystem.domain.usecases.SetVolumeUnitUseCase
import com.github.lucascalheiros.waterreminder.measuresystem.domain.usecases.SetWeightUnitUseCase
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

class MeasureSystemInjector: KoinComponent {
    fun getVolumeUnitUseCase(): GetVolumeUnitUseCase = get()
    fun getTemperatureUnitUseCase(): GetTemperatureUnitUseCase = get()
    fun getWeightUnitUseCase(): GetWeightUnitUseCase = get()
    fun setVolumeUnitUseCase(): SetVolumeUnitUseCase = get()
    fun setTemperatureUnitUseCase(): SetTemperatureUnitUseCase = get()
    fun setWeightUnitUseCase(): SetWeightUnitUseCase = get()
}