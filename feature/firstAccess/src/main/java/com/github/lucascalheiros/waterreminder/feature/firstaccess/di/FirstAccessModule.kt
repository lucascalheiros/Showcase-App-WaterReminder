package com.github.lucascalheiros.waterreminder.feature.firstaccess.di

import com.github.lucascalheiros.waterreminder.common.util.DispatchersQualifier
import com.github.lucascalheiros.waterreminder.feature.firstaccess.ui.activitylevelinput.ActivityLevelInputPresenter
import com.github.lucascalheiros.waterreminder.feature.firstaccess.ui.confirmationinput.ConfirmationInputPresenter
import com.github.lucascalheiros.waterreminder.feature.firstaccess.ui.firstaccessflow.FirstAccessFlowPresenter
import com.github.lucascalheiros.waterreminder.feature.firstaccess.ui.notificationinput.NotificationInputPresenter
import com.github.lucascalheiros.waterreminder.feature.firstaccess.ui.temperaturelevelinput.TemperatureLevelInputPresenter
import com.github.lucascalheiros.waterreminder.feature.firstaccess.ui.weightinput.WeightInputPresenter
import com.github.lucascalheiros.watertreminder.domain.firstaccess.di.firstAccessDomainModule
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val firstAccessModule = module {
    viewModel {
        ActivityLevelInputPresenter(
            get(DispatchersQualifier.Main),
            get(),
            get(),
        )
    }
    viewModel {
        TemperatureLevelInputPresenter(
            get(DispatchersQualifier.Main),
            get(),
            get(),
            get(),
            get(),
        )
    }
    viewModel {
        WeightInputPresenter(
            get(DispatchersQualifier.Main),
            get(),
            get(),
            get(),
            get(),
        )
    }
    viewModel {
        NotificationInputPresenter(
            get(DispatchersQualifier.Main),
            get(),
            get(),
            get(),
            get(),
            get(),
        )
    }
    viewModel {
        ConfirmationInputPresenter(
            get(DispatchersQualifier.Main),
            get(),
            get(),
            get(),
            get(),
            get(),
        )
    }
    viewModel {
        FirstAccessFlowPresenter(
            get(DispatchersQualifier.Main),
            get(),
            get(),
        )
    }
} + firstAccessDomainModule