package com.github.lucascalheiros.waterreminder.feature.settings.di

import com.github.lucascalheiros.waterreminder.common.util.DispatchersQualifier
import com.github.lucascalheiros.waterreminder.domain.remindnotifications.di.domainRemindNotificationsModule
import com.github.lucascalheiros.waterreminder.domain.userinformation.di.domainUserInformationModule
import com.github.lucascalheiros.waterreminder.domain.watermanagement.di.domainWaterManagementModule
import com.github.lucascalheiros.waterreminder.feature.settings.ui.managenotifications.ManageNotificationsPresenter
import com.github.lucascalheiros.waterreminder.feature.settings.ui.settings.SettingsPresenter
import com.github.lucascalheiros.waterreminder.feature.settings.ui.settings.sections.generalsection.GeneralSectionPresenter
import com.github.lucascalheiros.waterreminder.feature.settings.ui.settings.sections.profilesection.ProfileSectionPresenter
import com.github.lucascalheiros.waterreminder.feature.settings.ui.settings.sections.remindnotificationsection.RemindNotificationSectionPresenter
import kotlinx.coroutines.CoroutineDispatcher
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val settingsModule = module {
    viewModel {
        SettingsPresenter(
            get<CoroutineDispatcher>(DispatchersQualifier.Main),
        )
    }
    viewModel {
        GeneralSectionPresenter(
            get<CoroutineDispatcher>(DispatchersQualifier.Main),
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
        )
    }
    viewModel {
        ProfileSectionPresenter(
            get<CoroutineDispatcher>(DispatchersQualifier.Main),
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
        )
    }
    viewModel {
        RemindNotificationSectionPresenter(
            get<CoroutineDispatcher>(DispatchersQualifier.Main),
            get(),
            get(),
        )
    }
    viewModel {
        ManageNotificationsPresenter(
            get<CoroutineDispatcher>(DispatchersQualifier.Main),
            get(),
            get(),
            get(),
            get(),
            get(),
        )
    }
} + domainUserInformationModule + domainWaterManagementModule + domainRemindNotificationsModule
