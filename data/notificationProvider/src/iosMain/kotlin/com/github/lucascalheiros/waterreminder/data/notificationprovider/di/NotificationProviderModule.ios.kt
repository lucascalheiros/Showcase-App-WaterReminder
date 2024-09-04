package com.github.lucascalheiros.waterreminder.data.notificationprovider.di

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import co.touchlab.sqliter.DatabaseConfiguration
import com.github.lucascalheiros.waterreminder.common.util.datastore.createDataStore
import com.github.lucascalheiros.waterreminder.data.notificationprovider.ReminderNotificationDatabase
import com.github.lucascalheiros.waterreminder.data.notificationprovider.data.repositories.datasources.datastore.dataStoreFileName
import com.github.lucascalheiros.waterreminder.data.notificationprovider.data.repositories.datasources.datastore.notificationDatabaseName
import com.github.lucascalheiros.waterreminder.data.notificationprovider.framework.AlarmManagerWrapper
import org.koin.core.module.Module
import org.koin.dsl.bind
import org.koin.dsl.module

internal actual fun platformNotificationProviderModule(): Module = module {
    single(notificationDataStore) { createDataStore(dataStoreFileName) }
    single(notificationDbDriver) { createDriver() }
    single {
        object : AlarmManagerWrapper {
            override fun createAlarmSchedule(dayTimeInMinutes: Int) {
            }

            override fun cancelAlarmSchedule(dayTimeInMinutes: Int) {
            }
        }
    } bind AlarmManagerWrapper::class
}

internal fun createDriver(): SqlDriver {
    return NativeSqliteDriver(
        ReminderNotificationDatabase.Schema,
        notificationDatabaseName,
        onConfiguration = { config: DatabaseConfiguration ->
            config.copy(
                extendedConfig = DatabaseConfiguration.Extended(foreignKeyConstraints = true)
            )
        })
}