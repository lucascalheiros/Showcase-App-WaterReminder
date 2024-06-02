package com.github.lucascalheiros.waterreminder.domain.remindnotifications.integration

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.lucascalheiros.waterreminder.domain.remindnotifications.context
import com.github.lucascalheiros.waterreminder.domain.remindnotifications.dataStore
import com.github.lucascalheiros.waterreminder.domain.remindnotifications.di.domainRemindNotificationsModule
import com.github.lucascalheiros.waterreminder.domain.remindnotifications.dispatchersQualifierModule
import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.models.DayTime
import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.repositories.NotificationSchedulerRepository
import com.github.lucascalheiros.waterreminder.domain.remindnotifications.notificationDataStoreQualifier
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
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@RunWith(AndroidJUnit4::class)
class NotificationSchedulerRepositoryTest : KoinTest {

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
    fun whenCreateSchedulerSuspendListShouldReturnData() = runTest {
        val notificationSchedulerRepository: NotificationSchedulerRepository by inject()

        assertEquals(0, notificationSchedulerRepository.allRemindNotifications().size)

        val data = DayTime(2, 30)

        notificationSchedulerRepository.scheduleRemindNotification(data)

        assert(notificationSchedulerRepository.allRemindNotifications().contains(data))
    }

    @Test
    fun whenDeleteSchedulerSuspendListShouldReturnData() = runTest {
        val notificationSchedulerRepository: NotificationSchedulerRepository by inject()

        val data = DayTime(2, 30)

        notificationSchedulerRepository.scheduleRemindNotification(data)

        assert(notificationSchedulerRepository.allRemindNotifications().contains(data))

        notificationSchedulerRepository.cancelRemindNotification(data)

        assertEquals(0, notificationSchedulerRepository.allRemindNotifications().size)
    }

    @Test
    fun whenCreateSchedulerFlowListShouldReturnData() = runTest {
        val notificationSchedulerRepository: NotificationSchedulerRepository by inject()

        assertEquals(0, notificationSchedulerRepository.allRemindNotificationsFlow().first().size)

        val data = DayTime(2, 30)

        notificationSchedulerRepository.scheduleRemindNotification(data)

        assert(notificationSchedulerRepository.allRemindNotificationsFlow().first().contains(data))
    }

    @Test
    fun whenDeleteSchedulerFlowListShouldReturnData() = runTest {
        val notificationSchedulerRepository: NotificationSchedulerRepository by inject()

        val data = DayTime(2, 30)

        notificationSchedulerRepository.scheduleRemindNotification(data)

        assert(notificationSchedulerRepository.allRemindNotificationsFlow().first().contains(data))

        notificationSchedulerRepository.cancelRemindNotification(data)

        assertEquals(0, notificationSchedulerRepository.allRemindNotificationsFlow().first().size)
    }

    @Test
    fun defaultNotificationEnabledStateShouldBeTrue() = runTest {
        val notificationSchedulerRepository: NotificationSchedulerRepository by inject()

        assertTrue(notificationSchedulerRepository.isNotificationEnabled())

        assertTrue(notificationSchedulerRepository.isNotificationEnabledFlow().first())
    }


    @Test
    fun whenSetNotificationEnabledStateShouldReflectChange() = runTest {
        val notificationSchedulerRepository: NotificationSchedulerRepository by inject()

        notificationSchedulerRepository.setNotificationEnabled(false)

        assertFalse(notificationSchedulerRepository.isNotificationEnabled())

        assertFalse(notificationSchedulerRepository.isNotificationEnabledFlow().first())
    }
}
