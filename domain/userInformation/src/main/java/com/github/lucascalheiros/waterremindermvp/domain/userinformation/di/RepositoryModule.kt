package com.github.lucascalheiros.waterremindermvp.domain.userinformation.di

import com.github.lucascalheiros.waterremindermvp.domain.userinformation.domain.repositories.ThemeRepository
import com.github.lucascalheiros.waterremindermvp.domain.userinformation.data.repositories.ThemeRepositoryImpl
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module


internal val repositoryModule = module {
    singleOf(::ThemeRepositoryImpl) bind ThemeRepository::class
}