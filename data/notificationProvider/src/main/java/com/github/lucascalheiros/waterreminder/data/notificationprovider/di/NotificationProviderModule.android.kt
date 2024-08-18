package com.github.lucascalheiros.waterreminder.data.notificationprovider.di

import android.content.Context
import androidx.sqlite.db.SupportSQLiteDatabase
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.github.lucascalheiros.waterreminder.common.util.DispatchersQualifier
import com.github.lucascalheiros.waterreminder.common.util.datastore.createDataStore
import com.github.lucascalheiros.waterreminder.data.notificationprovider.ReminderNotificationDatabase
import com.github.lucascalheiros.waterreminder.data.notificationprovider.data.repositories.datasources.datastore.dataStoreFileName
import com.github.lucascalheiros.waterreminder.data.notificationprovider.data.repositories.datasources.datastore.notificationDatabaseName
import com.github.lucascalheiros.waterreminder.data.notificationprovider.framework.AlarmManagerWrapper
import com.github.lucascalheiros.waterreminder.data.notificationprovider.framework.AlarmManagerWrapperImpl
import com.github.lucascalheiros.waterreminder.data.notificationprovider.notification.helpers.AlarmManagerNotificationSetupHelper
import com.github.lucascalheiros.waterreminder.data.notificationprovider.notification.helpers.PreventNotificationByWeekDayHelper
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

internal actual fun platformNotificationProviderModule(): Module = module {
    single(notificationDataStore) { createDataStore(get<Context>(), dataStoreFileName) }
    single(notificationDbDriver) { createDriver(get<Context>()) }
    singleOf(::AlarmManagerWrapperImpl) bind AlarmManagerWrapper::class
    single {
        AlarmManagerNotificationSetupHelper(
            get(DispatchersQualifier.Io),
            get(),
            get(),
            get(),
        )
    }
    singleOf(::PreventNotificationByWeekDayHelper)
}

internal fun createDriver(context: Context): SqlDriver {
    return AndroidSqliteDriver(
        ReminderNotificationDatabase.Schema,
        context,
        notificationDatabaseName,
        callback =  object : AndroidSqliteDriver.Callback(ReminderNotificationDatabase.Schema) {
            override fun onOpen(db: SupportSQLiteDatabase) {
                db.setForeignKeyConstraintsEnabled(true)
            }
        })
}