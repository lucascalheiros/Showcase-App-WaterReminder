package com.github.lucascalheiros.waterremindermvp.feature.history.di

import com.github.lucascalheiros.waterremindermvp.common.util.DispatchersQualifier
import com.github.lucascalheiros.waterremindermvp.domain.watermanagement.di.domainWaterManagementModule
import com.github.lucascalheiros.waterremindermvp.feature.history.ui.history.HistoryPresenter
import kotlinx.coroutines.CoroutineDispatcher
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val historyModule = module {
    viewModel {
        HistoryPresenter(
            get<CoroutineDispatcher>(DispatchersQualifier.Main),
            get(),
            get(),
        )
    }
} + domainWaterManagementModule
