package com.github.lucascalheiros.waterreminder.data.home.di


import android.content.Context
import com.github.lucascalheiros.waterreminder.data.home.data.repositories.DrinkSortPositionRepositoryImpl
import com.github.lucascalheiros.waterreminder.data.home.data.repositories.WaterSourceSortPositionRepositoryImpl
import com.github.lucascalheiros.waterreminder.data.home.data.repositories.datasources.dao.PositionTrackDao
import com.github.lucascalheiros.waterreminder.data.home.data.repositories.datasources.datastore.dataStore
import com.github.lucascalheiros.waterreminder.domain.home.domain.repositories.DrinkSortPositionRepository
import com.github.lucascalheiros.waterreminder.domain.home.domain.repositories.WaterSourceSortPositionRepository
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.StringQualifier
import org.koin.dsl.bind
import org.koin.dsl.module

private val dataStoreQualifier = StringQualifier("HOME_DATA_STORE_QUALIFIER")

private val repositoryModule = module {
    singleOf(::DrinkSortPositionRepositoryImpl) bind DrinkSortPositionRepository::class
    singleOf(::WaterSourceSortPositionRepositoryImpl) bind WaterSourceSortPositionRepository::class
}

private val dataSourceModule = module {
    single(dataStoreQualifier) { get<Context>().dataStore }
    single { PositionTrackDao(get(dataStoreQualifier)) }
}

val homeDataModule = repositoryModule + dataSourceModule