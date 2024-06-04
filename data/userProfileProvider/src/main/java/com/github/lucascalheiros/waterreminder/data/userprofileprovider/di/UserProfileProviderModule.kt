package com.github.lucascalheiros.waterreminder.data.userprofileprovider.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.github.lucascalheiros.waterreminder.data.userprofileprovider.datasources.local.dao.UserProfileDao
import com.github.lucascalheiros.waterreminder.data.userprofileprovider.datasources.local.dao.impl.UserProfileDaoImpl
import com.github.lucascalheiros.waterreminder.data.userprofileprovider.datasources.local.datastore.dataStore
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

private val dataStoreQualifier = named("USER_PROFILE_DATASTORE")

val userProfileProviderModule = module {
    single(dataStoreQualifier) { get<Context>().dataStore }
    single {
        UserProfileDaoImpl(
            get<DataStore<Preferences>>(dataStoreQualifier)
        )
    } bind UserProfileDao::class
}