package com.github.lucascalheiros.waterreminder.measuresystem.di

import com.github.lucascalheiros.waterreminder.measuresystem.domain.repositories.*
import com.github.lucascalheiros.waterreminder.measuresystem.data.repositories.*
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module


internal val repositoryModule = module {
    singleOf(::MeasureSystemUnitRepositoryImpl) bind MeasureSystemUnitRepository::class
}