package com.github.lucascalheiros.waterreminder.feature.settings.ui.managenotifications

import androidx.lifecycle.SavedStateHandle
import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.models.DayTime
import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.models.WeekDay
import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.models.WeekState
import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.usecases.DeleteScheduledNotificationRequest
import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.usecases.DeleteScheduledNotificationUseCase
import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.usecases.GetScheduledNotificationsUseCase
import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.usecases.SetWeekDayNotificationStateUseCase
import com.github.lucascalheiros.waterreminder.feature.home.test.MainDispatcherRule
import com.github.lucascalheiros.waterreminder.feature.home.test.dispatchersQualifierModule
import com.github.lucascalheiros.waterreminder.feature.home.test.testDispatcher
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkClass
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.get
import org.koin.test.mock.MockProviderRule


@OptIn(ExperimentalCoroutinesApi::class)
class ManageNotificationsPresenterTest : KoinTest {
    @get:Rule
    val koinTestRule = KoinTestRule.create {
        printLogger()
        modules(testModule + dispatchersQualifierModule)
    }

    private val testModule = module {
        viewModel {
            ManageNotificationsPresenter(
                mainDispatcher = testDispatcher,
                getScheduledNotificationsUseCase = get(),
                deleteScheduledNotificationUseCase = get(),
                setWeekDayNotificationStateUseCase = get(),
            )
        }
        single { mockk<GetScheduledNotificationsUseCase>(relaxed = true) }
        single { mockk<DeleteScheduledNotificationUseCase>(relaxed = true) }
        single { mockk<SetWeekDayNotificationStateUseCase>(relaxed = true) }
        factory { SavedStateHandle() }
    }

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @get:Rule
    val mockProvider = MockProviderRule.create { clazz ->
        mockkClass(clazz)
    }

    private lateinit var presenter: ManageNotificationsPresenter

    private lateinit var view: ManageNotificationsContract.View

    @Before
    fun setup() {
        view = mockk(relaxed = true)
        presenter = get()
        presenter.attachView(view)
    }

    @Test
    fun `on remove schedule click removes notification`() = runTest {
        val dayTime = DayTime(hour = 8, minute = 30)

        presenter.onRemoveScheduleClick(dayTime)
        advanceUntilIdle()

        coVerify(exactly = 1) {
            get<DeleteScheduledNotificationUseCase>().invoke(
                DeleteScheduledNotificationRequest.Single(dayTime)
            )
        }
    }

    @Test
    fun `on notification days click shows week day picker`() = runTest(testDispatcher) {
        val dayTime = DayTime(hour = 9, minute = 45)
        every { get<GetScheduledNotificationsUseCase>().invoke(any()) }.returns(MutableStateFlow(mockk(relaxed = true)))

        presenter.onNotificationDaysClick(dayTime)
        advanceUntilIdle()

        verify { view.showNotificationWeekDaysPicker(any()) }
    }

    @Test
    fun `on notification week days change updates UI`() = runTest {
        val dayTime = DayTime(hour = 7, minute = 15)
        val newWeekDays = listOf(WeekDay.Monday, WeekDay.Wednesday)

        presenter.onNotificationWeekDaysChange(dayTime, newWeekDays)
        advanceUntilIdle()

        coVerify { get<SetWeekDayNotificationStateUseCase>().invoke(any(), any()) }
    }
}