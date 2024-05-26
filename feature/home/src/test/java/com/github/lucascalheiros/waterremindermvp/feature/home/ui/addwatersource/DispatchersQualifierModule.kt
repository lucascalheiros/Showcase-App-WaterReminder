package com.github.lucascalheiros.waterremindermvp.feature.home.ui.addwatersource

import com.github.lucascalheiros.waterremindermvp.common.util.DispatchersQualifier
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.test.StandardTestDispatcher
import org.koin.dsl.bind
import org.koin.dsl.module

val testDispatcher = StandardTestDispatcher()

val dispatchersQualifierModule = module {
    single(DispatchersQualifier.Main) { testDispatcher } bind CoroutineDispatcher::class
    single(DispatchersQualifier.Io) { testDispatcher } bind CoroutineDispatcher::class
    single(DispatchersQualifier.Default) { testDispatcher } bind CoroutineDispatcher::class
}