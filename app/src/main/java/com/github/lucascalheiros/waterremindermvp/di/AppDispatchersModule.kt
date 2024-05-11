package com.github.lucascalheiros.waterremindermvp.di

import com.github.lucascalheiros.waterremindermvp.common.util.DispatchersQualifier
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

val dispatchersQualifierModule = module {
    single(DispatchersQualifier.Main) { Dispatchers.Main }
    single(DispatchersQualifier.Io) { Dispatchers.IO }
    single(DispatchersQualifier.Default) { Dispatchers.Default }
}