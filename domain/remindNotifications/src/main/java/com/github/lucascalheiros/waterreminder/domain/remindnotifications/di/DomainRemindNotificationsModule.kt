package com.github.lucascalheiros.waterreminder.domain.remindnotifications.di

import com.github.lucascalheiros.waterreminder.data.notificationprovider.di.notificationProviderModule

val domainRemindNotificationsModule = listOf(useCaseModule, repositoryModule, notificationProviderModule)