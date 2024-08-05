package com.github.lucascalheiros.waterreminder.shared.di

import com.github.lucascalheiros.waterreminder.common.util.DispatchersQualifier
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.dsl.bind
import org.koin.dsl.module

val dispatchersQualifierModule = module {
    single(DispatchersQualifier.Main) { Dispatchers.Main } bind CoroutineDispatcher::class
    single(DispatchersQualifier.Io) { Dispatchers.IO } bind CoroutineDispatcher::class
    single(DispatchersQualifier.Default) { Dispatchers.Default } bind CoroutineDispatcher::class
}