package com.github.lucascalheiros.waterremindermvp.feature.settings.di


import com.github.lucascalheiros.waterremindermvp.common.util.DispatchersQualifier
import com.github.lucascalheiros.waterremindermvp.domain.remindnotifications.di.domainRemindNotificationsModule
import com.github.lucascalheiros.waterremindermvp.domain.userinformation.di.domainUserInformationModule
import com.github.lucascalheiros.waterremindermvp.domain.watermanagement.di.domainWaterManagementModule
import com.github.lucascalheiros.waterremindermvp.feature.settings.ui.settings.SettingsPresenter
import kotlinx.coroutines.CoroutineDispatcher
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val settingsModule = module {
    viewModel {
        SettingsPresenter(
            get<CoroutineDispatcher>(DispatchersQualifier.Main),
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
        )
    }
} + domainUserInformationModule + domainWaterManagementModule + domainRemindNotificationsModule
