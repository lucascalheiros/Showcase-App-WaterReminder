package com.github.lucascalheiros.waterremindermvp.feature.home.di

import com.github.lucascalheiros.waterremindermvp.common.util.DispatchersQualifier
import com.github.lucascalheiros.waterremindermvp.domain.watermanagement.di.domainWaterManagementModule
import com.github.lucascalheiros.waterremindermvp.feature.home.ui.addwatersource.AddWaterSourcePresenter
import com.github.lucascalheiros.waterremindermvp.feature.home.ui.home.HomePresenter
import kotlinx.coroutines.CoroutineDispatcher
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val homeModule = module {
    viewModel {
        HomePresenter(
            get<CoroutineDispatcher>(DispatchersQualifier.Main),
            get(),
            get(),
            get(),
            get(),
        )
    }
    viewModel {
        AddWaterSourcePresenter(
            get<CoroutineDispatcher>(DispatchersQualifier.Main),
            get(),
            get(),
            get(),
            get(),
        )
    }
} + domainWaterManagementModule
