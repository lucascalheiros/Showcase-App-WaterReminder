package com.github.lucascalheiros.waterreminder.data.userprofileprovider.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.github.lucascalheiros.waterreminder.data.userprofileprovider.data.repositories.UserProfileRepositoryImpl
import com.github.lucascalheiros.waterreminder.data.userprofileprovider.data.repositories.datasources.UserProfileDataSource
import com.github.lucascalheiros.waterreminder.data.userprofileprovider.data.repositories.datasources.datastore.dataStore
import com.github.lucascalheiros.waterreminder.domain.userinformation.domain.repositories.UserProfileRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module


private val repositoryModule = module {
    singleOf(::UserProfileRepositoryImpl) bind UserProfileRepository::class
}

private val dataSourceModule = module {
    single(dataStoreQualifier) { get<Context>().dataStore }
    single {
        UserProfileDataSource(
            get<DataStore<Preferences>>(dataStoreQualifier)
        )
    }
}

val userProfileProviderModule = repositoryModule + dataSourceModule