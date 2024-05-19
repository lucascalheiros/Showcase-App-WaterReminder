package com.github.lucascalheiros.waterremindermvp.di

import com.github.lucascalheiros.waterremindermvp.domain.remindnotifications.di.domainRemindNotificationsModule
import com.github.lucascalheiros.waterremindermvp.feature.history.di.historyModule
import com.github.lucascalheiros.waterremindermvp.feature.home.di.homeModule
import com.github.lucascalheiros.waterremindermvp.feature.settings.di.settingsModule

private val featureModules = homeModule +
        historyModule +
        settingsModule

val appModule = featureModules +
        dispatchersQualifierModule +
        domainRemindNotificationsModule
