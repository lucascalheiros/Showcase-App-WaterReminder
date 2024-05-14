package com.github.lucascalheiros.waterremindermvp.di

import com.github.lucascalheiros.waterremindermvp.domain.remindnotifications.di.domainRemindNotificationsModule
import com.github.lucascalheiros.waterremindermvp.feature.home.di.homeModule

val appModule = homeModule +
        dispatchersQualifierModule +
        domainRemindNotificationsModule
