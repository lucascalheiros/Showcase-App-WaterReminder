package com.github.lucascalheiros.waterreminder.domain.history.di

import com.github.lucascalheiros.waterreminder.domain.history.domain.usecases.GetHistoryChartDataUseCase
import com.github.lucascalheiros.waterreminder.domain.history.domain.usecases.impl.GetHistoryChartDataUseCaseImpl
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val historyDomainModule = module {
    singleOf(::GetHistoryChartDataUseCaseImpl)  bind GetHistoryChartDataUseCase::class
}