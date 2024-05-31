package com.github.lucascalheiros.waterremindermvp.domain.remindnotifications.integration

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.lucascalheiros.waterremindermvp.domain.remindnotifications.context
import com.github.lucascalheiros.waterremindermvp.domain.remindnotifications.dataStore
import com.github.lucascalheiros.waterremindermvp.domain.remindnotifications.di.domainRemindNotificationsModule
import com.github.lucascalheiros.waterremindermvp.domain.remindnotifications.dispatchersQualifierModule
import com.github.lucascalheiros.waterremindermvp.domain.remindnotifications.domain.models.WeekDay
import com.github.lucascalheiros.waterremindermvp.domain.remindnotifications.domain.repositories.WeekDayNotificationStateRepository
import com.github.lucascalheiros.waterremindermvp.domain.remindnotifications.notificationDataStoreQualifier
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertFalse
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
class WeekDayNotificationStateRepositoryTest : KoinTest {

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        printLogger()
        androidContext(context)
        modules(domainRemindNotificationsModule + dispatchersQualifierModule + notificationProviderModule)
    }

    private val notificationProviderModule = module {
        single(notificationDataStoreQualifier) { get<Context>().dataStore }
    }

    @Before
    fun setup() = runTest {
        get<DataStore<Preferences>>(notificationDataStoreQualifier).edit {
            it.clear()
        }
    }

    @Test
    fun onCleanInstallAllDaysShouldBeEnabledInSuspendList() = runTest {
        val weekDayNotificationStateRepository: WeekDayNotificationStateRepository by inject()

        val data = weekDayNotificationStateRepository.weekDayNotificationState()

        assertEquals(7, data.size)

        assertEquals(7, WeekDay.entries.intersect(data.map { it.weekDay }.toSet()).size)

        assert(data.all { it.isEnabled })
    }

    @Test
    fun onCleanInstallAllDaysShouldBeEnabledInFlowList() = runTest {
        val weekDayNotificationStateRepository: WeekDayNotificationStateRepository by inject()

        val data = weekDayNotificationStateRepository.weekDayNotificationStateFlow().first()

        assertEquals(7, data.size)

        assertEquals(7, WeekDay.entries.intersect(data.map { it.weekDay }.toSet()).size)

        assert(data.all { it.isEnabled })
    }

    @Test
    fun whenUpdateStateShouldReflectInSuspendList() = runTest {
        val weekDayNotificationStateRepository: WeekDayNotificationStateRepository by inject()

        weekDayNotificationStateRepository.updateWeekDayState(WeekDay.Monday, false)

        val data = weekDayNotificationStateRepository.weekDayNotificationState()

        assertFalse(data.find { it.weekDay == WeekDay.Monday }!!.isEnabled)
    }

    @Test
    fun whenUpdateStateShouldReflectInFlowList() = runTest {
        val weekDayNotificationStateRepository: WeekDayNotificationStateRepository by inject()

        weekDayNotificationStateRepository.updateWeekDayState(WeekDay.Monday, false)

        val data = weekDayNotificationStateRepository.weekDayNotificationStateFlow().first()

        assertFalse(data.find { it.weekDay == WeekDay.Monday }!!.isEnabled)
    }

}