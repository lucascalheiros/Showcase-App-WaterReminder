package com.github.lucascalheiros.waterreminder.di

import com.github.lucascalheiros.waterreminder.domain.remindnotifications.di.domainRemindNotificationsModule
import com.github.lucascalheiros.waterreminder.feature.history.di.historyModule
import com.github.lucascalheiros.waterreminder.feature.home.di.homeModule
import com.github.lucascalheiros.waterreminder.feature.settings.di.settingsModule

private val featureModules = homeModule +
        historyModule +
        settingsModule

val appModule = featureModules +
        dispatchersQualifierModule +
        domainRemindNotificationsModule
