package com.github.lucascalheiros.waterreminder.data.home.di

import com.github.lucascalheiros.waterreminder.data.home.data.repositories.DrinkSortPositionRepositoryImpl
import com.github.lucascalheiros.waterreminder.data.home.data.repositories.WaterSourceSortPositionRepositoryImpl
import com.github.lucascalheiros.waterreminder.data.home.data.repositories.datasources.dao.PositionTrackDao
import com.github.lucascalheiros.waterreminder.domain.home.domain.repositories.DrinkSortPositionRepository
import com.github.lucascalheiros.waterreminder.domain.home.domain.repositories.WaterSourceSortPositionRepository
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

private val repositoryModule = module {
    singleOf(::DrinkSortPositionRepositoryImpl) bind DrinkSortPositionRepository::class
    singleOf(::WaterSourceSortPositionRepositoryImpl) bind WaterSourceSortPositionRepository::class
}

internal expect fun homeDataStoreModule(): Module

private val dataSourceModule = module {
    single { PositionTrackDao(get(dataStoreQualifier)) }
}

val homeDataModule = repositoryModule + dataSourceModule + homeDataStoreModule()