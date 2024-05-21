package com.github.lucascalheiros.waterremindermvp.data.themewrapper.di

import com.github.lucascalheiros.waterremindermvp.data.themewrapper.framework.ThemeWrapper
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val themeWrapperModule = module {
    singleOf(::ThemeWrapper)
}