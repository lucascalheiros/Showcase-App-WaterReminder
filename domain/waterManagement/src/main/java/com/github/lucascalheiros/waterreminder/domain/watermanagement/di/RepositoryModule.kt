package com.github.lucascalheiros.waterreminder.domain.watermanagement.di

import com.github.lucascalheiros.waterreminder.domain.watermanagement.data.repositories.ConsumedWaterRepositoryImpl
import com.github.lucascalheiros.waterreminder.domain.watermanagement.data.repositories.DailyWaterConsumptionRepositoryImpl
import com.github.lucascalheiros.waterreminder.domain.watermanagement.data.repositories.WaterSourceRepositoryImpl
import com.github.lucascalheiros.waterreminder.domain.watermanagement.data.repositories.WaterSourceTypeRepositoryImpl
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.repositories.ConsumedWaterRepository
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.repositories.DailyWaterConsumptionRepository
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.repositories.WaterSourceRepository
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.repositories.WaterSourceTypeRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

internal val repositoryModule = module {
    singleOf(::ConsumedWaterRepositoryImpl) bind ConsumedWaterRepository::class
    singleOf(::DailyWaterConsumptionRepositoryImpl) bind DailyWaterConsumptionRepository::class
    singleOf(::WaterSourceRepositoryImpl) bind WaterSourceRepository::class
    singleOf(::WaterSourceTypeRepositoryImpl) bind WaterSourceTypeRepository::class
}