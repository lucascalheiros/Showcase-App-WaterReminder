package com.github.lucascalheiros.waterreminder.domain.home

import com.github.lucascalheiros.waterreminder.domain.home.domain.usecases.GetSortedDrinkUseCase
import com.github.lucascalheiros.waterreminder.domain.home.domain.usecases.GetSortedWaterSourceUseCase
import com.github.lucascalheiros.waterreminder.domain.home.domain.usecases.SetDrinkSortPositionUseCase
import com.github.lucascalheiros.waterreminder.domain.home.domain.usecases.SetWaterSourceSortPositionUseCase
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

class HomeInjector: KoinComponent {
    fun getSortedWaterSourceUseCase(): GetSortedWaterSourceUseCase = get()
    fun getSortedDrinkUseCase(): GetSortedDrinkUseCase = get()
    fun setWaterSourceSortPositionUseCase(): SetWaterSourceSortPositionUseCase = get()
    fun stDrinkSortPositionUseCase(): SetDrinkSortPositionUseCase = get()
}