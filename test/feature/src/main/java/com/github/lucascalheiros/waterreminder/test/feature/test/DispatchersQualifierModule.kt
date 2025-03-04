package com.github.lucascalheiros.waterreminder.feature.home.test

import com.github.lucascalheiros.waterreminder.common.util.DispatchersQualifier
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.koin.dsl.bind
import org.koin.dsl.module

val testDispatcher = StandardTestDispatcher()

val dispatchersQualifierModule = module {
    single(DispatchersQualifier.Main) { testDispatcher } bind CoroutineDispatcher::class
    single(DispatchersQualifier.Io) { testDispatcher } bind CoroutineDispatcher::class
    single(DispatchersQualifier.Default) { testDispatcher } bind CoroutineDispatcher::class
}

@OptIn(ExperimentalCoroutinesApi::class)
val unconfinedTestDispatcher = UnconfinedTestDispatcher()

val unconfinedDispatchersQualifierModule = module {
    single(DispatchersQualifier.Main) { unconfinedTestDispatcher } bind CoroutineDispatcher::class
    single(DispatchersQualifier.Io) { unconfinedTestDispatcher } bind CoroutineDispatcher::class
    single(DispatchersQualifier.Default) { unconfinedTestDispatcher } bind CoroutineDispatcher::class
}