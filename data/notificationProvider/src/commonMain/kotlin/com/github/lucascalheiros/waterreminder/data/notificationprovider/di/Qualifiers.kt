package com.github.lucascalheiros.waterreminder.data.notificationprovider.di

import org.koin.core.qualifier.named

internal val notificationDbDriver = named("NOTIFICATION_DB_DRIVER")

internal val notificationDataStore = named("NOTIFICATION_DATASTORE")