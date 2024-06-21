package com.github.lucascalheiros.waterreminder

import androidx.lifecycle.SavedStateHandle
import com.github.lucascalheiros.waterreminder.di.appModule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.koinApplication
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.check.checkModules
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

@RunWith(RobolectricTestRunner::class)
class CheckModulesTest : KoinTest {

    private val savedStateModule = module {
        factory { SavedStateHandle() }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun verifyKoinApp() {
        Dispatchers.setMain(StandardTestDispatcher())

        koinApplication {
            modules(appModule + savedStateModule)
            androidContext(RuntimeEnvironment.getApplication())
            checkModules()
        }
    }
}