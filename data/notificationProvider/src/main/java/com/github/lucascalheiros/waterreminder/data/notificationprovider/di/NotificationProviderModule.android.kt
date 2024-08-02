package com.github.lucascalheiros.waterreminder.data.notificationprovider.di

import android.content.Context
import com.github.lucascalheiros.waterreminder.common.util.DispatchersQualifier
import com.github.lucascalheiros.waterreminder.common.util.datastore.createDataStore
import com.github.lucascalheiros.waterreminder.data.notificationprovider.data.repositories.datasources.datastore.dataStoreFileName
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