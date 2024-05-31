package com.github.lucascalheiros.waterremindermvp.domain.userinformation.integration

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.lucascalheiros.waterremindermvp.domain.userinformation.context
import com.github.lucascalheiros.waterremindermvp.domain.userinformation.dataStore
import com.github.lucascalheiros.waterremindermvp.domain.userinformation.di.domainUserInformationModule
import com.github.lucascalheiros.waterremindermvp.domain.userinformation.dispatchersQualifierModule
import com.github.lucascalheiros.waterremindermvp.domain.userinformation.domain.models.AppTheme
import com.github.lucascalheiros.waterremindermvp.domain.userinformation.domain.repositories.ThemeRepository
import com.github.lucascalheiros.waterremindermvp.domain.userinformation.themeDataStoreQualifier
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.get
import org.koin.test.inject
import kotlin.test.assertEquals

@RunWith(AndroidJUnit4::class)
class ThemeRepositoryTest : KoinTest {

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        printLogger()
        androidContext(context)
        modules(domainUserInformationModule + dispatchersQualifierModule + dataStoreModule)
    }

    private val dataStoreModule = module {
        single(themeDataStoreQualifier) { get<Context>().dataStore }
    }

    @Before
    fun setup() = runTest {
        get<DataStore<Preferences>>(themeDataStoreQualifier).edit {
            it.clear()
        }
    }

    @Test
    fun defaultValueShouldBeAuto() = runTest {
        val themeRepository: ThemeRepository by inject()

        assertEquals(AppTheme.Auto, themeRepository.getTheme().first())
    }

    @Test
    fun whenValueIsUpdatedItShouldReflect() = runTest {
        val themeRepository: ThemeRepository by inject()

        themeRepository.setTheme(AppTheme.Dark)

        assertEquals(AppTheme.Dark, themeRepository.getTheme().first())

        themeRepository.setTheme(AppTheme.Auto)

        assertEquals(AppTheme.Auto, themeRepository.getTheme().first())

        themeRepository.setTheme(AppTheme.Light)

        assertEquals(AppTheme.Light, themeRepository.getTheme().first())
    }

}
