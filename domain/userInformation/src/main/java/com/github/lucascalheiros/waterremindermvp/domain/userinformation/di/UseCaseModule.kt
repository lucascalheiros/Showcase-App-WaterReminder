package com.github.lucascalheiros.waterremindermvp.domain.userinformation.di


import com.github.lucascalheiros.waterremindermvp.domain.userinformation.domain.usecases.GetThemeUseCase
import com.github.lucascalheiros.waterremindermvp.domain.userinformation.domain.usecases.SetThemeUseCase
import com.github.lucascalheiros.waterremindermvp.domain.userinformation.domain.usecases.impl.GetThemeUseCaseImpl
import com.github.lucascalheiros.waterremindermvp.domain.userinformation.domain.usecases.impl.SetThemeUseCaseImpl
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

internal val useCaseModule = module {
    singleOf(::GetThemeUseCaseImpl) bind GetThemeUseCase::class
    singleOf(::SetThemeUseCaseImpl) bind SetThemeUseCase::class

}