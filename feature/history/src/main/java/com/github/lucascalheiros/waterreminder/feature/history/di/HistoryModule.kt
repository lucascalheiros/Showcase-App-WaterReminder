package com.github.lucascalheiros.waterreminder.feature.history.di

import com.github.lucascalheiros.waterreminder.common.util.DispatchersQualifier
import com.github.lucascalheiros.waterreminder.domain.history.di.historyDomainModule
import com.github.lucascalheiros.waterreminder.domain.watermanagement.di.domainWaterManagementModule
import com.github.lucascalheiros.waterreminder.feature.history.ui.adddrinkentry.AddDrinkEntryPresenter
import com.github.lucascalheiros.waterreminder.feature.history.ui.history.HistoryPresenter
import kotlinx.coroutines.CoroutineDispatcher
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val historyModule = module {
    viewModel {
        HistoryPresenter(
            get<CoroutineDispatcher>(DispatchersQualifier.Main),
            get(),
            get(),
            get(),
        )
    }
    viewModel {
        AddDrinkEntryPresenter(
            get<CoroutineDispatcher>(DispatchersQualifier.Main),
            get(),
            get(),
            get(),
            get(),
            get(),
        )
    }
} + domainWaterManagementModule + historyDomainModule
