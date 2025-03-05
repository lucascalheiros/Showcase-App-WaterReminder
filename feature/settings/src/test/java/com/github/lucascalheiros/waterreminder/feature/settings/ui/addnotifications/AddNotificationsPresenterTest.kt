package com.github.lucascalheiros.waterreminder.feature.settings.ui.addnotifications

import androidx.lifecycle.SavedStateHandle
import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.models.DayTime
import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.models.WeekDay
import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.models.WeekState
import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.usecases.CreateScheduleNotificationUseCase
import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.usecases.DeleteScheduledNotificationUseCase
import com.github.lucascalheiros.waterreminder.feature.home.test.MainDispatcherRule
import com.github.lucascalheiros.waterreminder.feature.home.test.dispatchersQualifierModule
import com.github.lucascalheiros.waterreminder.feature.home.test.testDispatcher
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.mockkClass
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalTime
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
class AddNotificationsPresenterTest : KoinTest {
    @get:Rule
    val koinTestRule = KoinTestRule.create {
        printLogger()
        modules(testModule + dispatchersQualifierModule)
    }

    private val testModule = module {
        viewModel {
            AddNotificationsPresenter(
                mainDispatcher = testDispatcher,
                createScheduleNotificationUseCase = get(),
                deleteScheduledNotificationUseCase = get(),
            )
        }
        single { mockk<CreateScheduleNotificationUseCase>(relaxed = true) }
        single { mockk<DeleteScheduledNotificationUseCase>(relaxed = true) }
        factory { SavedStateHandle() }
    }

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @get:Rule
    val mockProvider = MockProviderRule.create { clazz ->
        mockkClass(clazz)
    }

    private lateinit var presenter: AddNotificationsPresenter

    private lateinit var view: AddNotificationsContract.View

    @Before
    fun setup() {
        view = mockk(relaxed = true)
        presenter = get()
        presenter.attachView(view)
    }

    @Test
    fun `on start time click show picker`() = runTest(testDispatcher) {
        presenter.onStartTimeClick()
        advanceUntilIdle()

        verify(exactly = 1) {
            view.showTimePicker(any())
        }
    }

    @Test
    fun `on stop time click show picker`() = runTest(testDispatcher) {
        presenter.onStopTimeClick()
        advanceUntilIdle()

        verify(exactly = 1) {
            view.showTimePicker(any())
        }
    }

    @Test
    fun `on period time click show picker`() = runTest(testDispatcher) {
        presenter.onPeriodTimeClick()
        advanceUntilIdle()

        verify(exactly = 1) {
            view.showTimePicker(any())
        }
    }

    @Test
    fun `on single time click show picker`() = runTest(testDispatcher) {
        presenter.onSingleTimeClick()
        advanceUntilIdle()

        verify(exactly = 1) {
            view.showTimePicker(any<TimePickerRequest.Single>())
        }
    }

    @Test
    fun `on week days click show picker`() = runTest(testDispatcher) {
        presenter.onWeekDaysClick()
        advanceUntilIdle()

        verify(exactly = 1) {
            view.showWeekDaysPicker(any())
        }
    }

    @Test
    fun `on confirm send request and dismiss`() = runTest(testDispatcher) {
        presenter.onConfirm()
        advanceUntilIdle()

        coVerify(exactly = 1) {
            get<CreateScheduleNotificationUseCase>().invoke(any<DayTime>(), any<WeekState>())
            view.dismissBottomSheet()
        }
    }

    @Test
    fun `on cancel dismiss bottom sheet`() = runTest(testDispatcher) {
        presenter.onCancel()
        advanceUntilIdle()

        verify(exactly = 1) {
            view.dismissBottomSheet()
        }
    }

    @Test
    fun `on set start time update ui`() = runTest(testDispatcher) {
        val time = LocalTime(8, 0)
        presenter.onSetInputMode(AddNotificationInputMode.Multiple)
        presenter.onSetStartTime(time)
        advanceUntilIdle()

        verify(exactly = 1) {
            view.setAddNotificationData(
                match {
                    (it as AddNotificationData.Multiple).startTime == time
                }
            )
        }
    }

    @Test
    fun `on set stop time update ui`() = runTest(testDispatcher) {
        val time = LocalTime(20, 0)
        presenter.onSetInputMode(AddNotificationInputMode.Multiple)
        presenter.onSetStopTime(time)
        advanceUntilIdle()

        verify(exactly = 1) {
            view.setAddNotificationData(
                match {
                    (it as AddNotificationData.Multiple).stopTime == time
                }
            )
        }
    }

    @Test
    fun `on set period time update ui`() = runTest(testDispatcher) {
        val time = LocalTime(2, 0)
        presenter.onSetInputMode(AddNotificationInputMode.Multiple)
        presenter.onSetPeriodTime(time)
        advanceUntilIdle()

        verify(exactly = 1) {
            view.setAddNotificationData(
                match {
                    (it as AddNotificationData.Multiple).periodTime == time
                }
            )
        }
    }

    @Test
    fun `on set single time update ui`() = runTest(testDispatcher) {
        val time = LocalTime(10, 0)
        presenter.onSetSingleTime(time)
        advanceUntilIdle()

        verify(exactly = 1) {
            view.setAddNotificationData(
                match {
                    (it as AddNotificationData.Single).time == time
                }
            )
        }
    }

    @Test
    fun `on set delete others update ui`() = runTest(testDispatcher) {
        presenter.onSetInputMode(AddNotificationInputMode.Multiple)
        advanceUntilIdle()
        presenter.onSetDeleteOthers(true)
        advanceUntilIdle()

        verify(exactly = 1) {
            view.setAddNotificationData(
                match {
                    (it as AddNotificationData.Multiple).deleteOthersState
                }
            )
        }
    }

    @Test
    fun `on week days change update ui for single`() = runTest(testDispatcher) {
        presenter.onSetInputMode(AddNotificationInputMode.Single)
        advanceUntilIdle()
        val weekDays = listOf(WeekDay.Monday, WeekDay.Wednesday)
        presenter.onWeekDaysChange(weekDays)
        advanceUntilIdle()

        verify(exactly = 1) {
            view.setAddNotificationData(match {
                (it as AddNotificationData.Single).selectedWeekDays == weekDays
            })
        }
    }

    @Test
    fun `on week days change update ui for multiple`() = runTest(testDispatcher) {
        presenter.onSetInputMode(AddNotificationInputMode.Multiple)
        advanceUntilIdle()
        val weekDays = listOf(WeekDay.Monday, WeekDay.Wednesday)
        presenter.onWeekDaysChange(weekDays)
        advanceUntilIdle()

        verify(exactly = 1) {
            view.setAddNotificationData(match {
                (it as AddNotificationData.Multiple).selectedWeekDays == weekDays
            })
        }
    }
}
