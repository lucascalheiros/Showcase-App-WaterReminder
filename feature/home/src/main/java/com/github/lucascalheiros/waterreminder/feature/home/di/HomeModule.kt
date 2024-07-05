package com.github.lucascalheiros.waterreminder.feature.home.di

import com.github.lucascalheiros.waterreminder.common.util.DispatchersQualifier
import com.github.lucascalheiros.waterreminder.domain.home.di.homeDomainModule
import com.github.lucascalheiros.waterreminder.domain.watermanagement.di.domainWaterManagementModule
import com.github.lucascalheiros.waterreminder.feature.home.ui.adddrink.AddDrinkPresenter
import com.github.lucascalheiros.waterreminder.feature.home.ui.addwatersource.AddWaterSourcePresenter
import com.github.lucascalheiros.waterreminder.feature.home.ui.drinkshortcut.DrinkShortcutPresenter
import com.github.lucascalheiros.waterreminder.feature.home.ui.home.HomePresenter
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
            get(),
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
            get(),
        )
    }
    viewModel {
        DrinkShortcutPresenter(
            get<CoroutineDispatcher>(DispatchersQualifier.Main),
            get(),
            get(),
            get(),
            get(),
            get(),
        )
    }
    viewModel {
        AddDrinkPresenter(
            get<CoroutineDispatcher>(DispatchersQualifier.Main),
            get(),
            get(),
        )
    }
} + domainWaterManagementModule + homeDomainModule
