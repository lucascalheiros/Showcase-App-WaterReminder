package com.github.lucascalheiros.waterreminder.domain.userinformation.di

import com.github.lucascalheiros.waterreminder.domain.userinformation.domain.repositories.*
import com.github.lucascalheiros.waterreminder.domain.userinformation.data.repositories.*
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module


internal val repositoryModule = module {
    singleOf(::ThemeRepositoryImpl) bind ThemeRepository::class
    singleOf(::UserProfileRepositoryImpl) bind UserProfileRepository::class
}