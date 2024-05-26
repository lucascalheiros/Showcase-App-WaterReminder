package com.github.lucascalheiros.waterremindermvp

import android.content.Context
import com.github.lucascalheiros.waterremindermvp.di.appModule
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Test
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.koinApplication
import org.koin.test.KoinTest
import org.koin.test.check.checkModules

class CheckModulesTest : KoinTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun verifyKoinApp() {
        Dispatchers.setMain(StandardTestDispatcher())

        koinApplication {
            modules(appModule)
            androidContext(mockk<Context>())
            checkModules()
        }
    }
}