package com.github.lucascalheiros.waterreminder.domain.home.di

import com.github.lucascalheiros.waterreminder.domain.home.domain.usecases.GetSortedDrinkUseCase
import com.github.lucascalheiros.waterreminder.domain.home.domain.usecases.GetSortedWaterSourceUseCase
import com.github.lucascalheiros.waterreminder.domain.home.domain.usecases.SetDrinkSortPositionUseCase
import com.github.lucascalheiros.waterreminder.domain.home.domain.usecases.SetWaterSourceSortPositionUseCase
import com.github.lucascalheiros.waterreminder.domain.home.domain.usecases.impl.GetSortedDrinkUseCaseImpl
import com.github.lucascalheiros.waterreminder.domain.home.domain.usecases.impl.GetSortedWaterSourceUseCaseImpl
import com.github.lucascalheiros.waterreminder.domain.home.domain.usecases.impl.SetDrinkSortPositionUseCaseImpl
import com.github.lucascalheiros.waterreminder.domain.home.domain.usecases.impl.SetWaterSourceSortPositionUseCaseImpl
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val homeDomainModule = module {
    singleOf(::GetSortedWaterSourceUseCaseImpl)  bind GetSortedWaterSourceUseCase::class
    singleOf(::GetSortedDrinkUseCaseImpl)  bind GetSortedDrinkUseCase::class
    singleOf(::SetWaterSourceSortPositionUseCaseImpl)  bind SetWaterSourceSortPositionUseCase::class
    singleOf(::SetDrinkSortPositionUseCaseImpl)  bind SetDrinkSortPositionUseCase::class
}