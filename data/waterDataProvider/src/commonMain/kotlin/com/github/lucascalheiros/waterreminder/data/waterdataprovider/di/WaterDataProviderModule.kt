package com.github.lucascalheiros.waterreminder.data.waterdataprovider.di

import com.github.lucascalheiros.waterreminder.data.waterdataprovider.WaterDatabase
import com.github.lucascalheiros.waterreminder.data.waterdataprovider.data.repositories.ConsumedWaterRepositoryImpl
import com.github.lucascalheiros.waterreminder.data.waterdataprovider.data.repositories.DailyWaterConsumptionRepositoryImpl
import com.github.lucascalheiros.waterreminder.data.waterdataprovider.data.repositories.WaterSourceRepositoryImpl
import com.github.lucascalheiros.waterreminder.data.waterdataprovider.data.repositories.WaterSourceTypeRepositoryImpl
import com.github.lucascalheiros.waterreminder.data.waterdataprovider.data.repositories.datasources.dao.ConsumedWaterDao
import com.github.lucascalheiros.waterreminder.data.waterdataprovider.data.repositories.datasources.dao.DailyWaterConsumptionDao
import com.github.lucascalheiros.waterreminder.data.waterdataprovider.data.repositories.datasources.dao.WaterSourceDao
import com.github.lucascalheiros.waterreminder.data.waterdataprovider.data.repositories.datasources.dao.WaterSourceTypeDao
import com.github.lucascalheiros.waterreminder.data.waterdataprovider.data.repositories.datasources.dao.impl.ConsumedWaterDaoImpl
import com.github.lucascalheiros.waterreminder.data.waterdataprovider.data.repositories.datasources.dao.impl.DailyWaterConsumptionDaoImpl
import com.github.lucascalheiros.waterreminder.data.waterdataprovider.data.repositories.datasources.dao.impl.WaterSourceDaoImpl
import com.github.lucascalheiros.waterreminder.data.waterdataprovider.data.repositories.datasources.dao.impl.WaterSourceTypeDaoImpl
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.repositories.ConsumedWaterRepository
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.repositories.DailyWaterConsumptionRepository
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.repositories.WaterSourceRepository
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.repositories.WaterSourceTypeRepository
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

private val repositoryModule = module {
    singleOf(::ConsumedWaterRepositoryImpl) bind ConsumedWaterRepository::class
    single {
        DailyWaterConsumptionRepositoryImpl(get())
    } bind DailyWaterConsumptionRepository::class
    singleOf(::WaterSourceRepositoryImpl) bind WaterSourceRepository::class
    single {
        WaterSourceTypeRepositoryImpl(get())
    } bind WaterSourceTypeRepository::class
}

internal expect fun waterDataDbModule(): Module

private val dataSourceModule = module {
    single { WaterDatabase(get(waterDataDbDriver)) }
    singleOf(::ConsumedWaterDaoImpl) bind ConsumedWaterDao::class
    singleOf(::DailyWaterConsumptionDaoImpl) bind DailyWaterConsumptionDao::class
    singleOf(::WaterSourceTypeDaoImpl) bind WaterSourceTypeDao::class
    singleOf(::WaterSourceDaoImpl) bind WaterSourceDao::class
}

val waterDataProviderModule = repositoryModule + dataSourceModule + waterDataDbModule()