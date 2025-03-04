package com.github.lucascalheiros.waterreminder.feature.history.ui.history

import androidx.lifecycle.SavedStateHandle
import com.github.lucascalheiros.waterreminder.domain.history.domain.models.ChartOption
import com.github.lucascalheiros.waterreminder.domain.history.domain.models.HistoryChartData
import com.github.lucascalheiros.waterreminder.domain.history.domain.usecases.GetHistoryChartDataUseCase
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.models.DailyWaterConsumptionSummary
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.DeleteConsumedWaterUseCase
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.GetDailyWaterConsumptionSummaryUseCase
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.requests.SummaryRequest
import com.github.lucascalheiros.waterreminder.feature.home.test.MainDispatcherRule
import com.github.lucascalheiros.waterreminder.feature.home.test.dispatchersQualifierModule
import com.github.lucascalheiros.waterreminder.feature.home.test.testDispatcher
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkClass
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
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
class HistoryPresenterTest : KoinTest {
    @get:Rule
    val koinTestRule = KoinTestRule.create {
        printLogger()
        modules(testModule + dispatchersQualifierModule)
    }

    private val testModule = module {
        viewModel {
            HistoryPresenter(
                mainDispatcher = testDispatcher,
                get(),
                get(),
                get(),
            )
        }
        single { mockk<GetDailyWaterConsumptionSummaryUseCase>() }
        single { mockk<DeleteConsumedWaterUseCase>() }
        single { mockk<GetHistoryChartDataUseCase>() }
        factory { SavedStateHandle() }
    }

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @get:Rule
    val mockProvider = MockProviderRule.create { clazz ->
        mockkClass(clazz)
    }

    private lateinit var presenter: HistoryPresenter

    private lateinit var view: HistoryContract.View

    private lateinit var lastSummaries: MutableSharedFlow<List<DailyWaterConsumptionSummary>>
    private lateinit var historyChart: MutableSharedFlow<HistoryChartData>

    @Before
    fun setup() {
        lastSummaries = MutableSharedFlow(replay = 1)
        historyChart = MutableSharedFlow(replay = 1)
        every { get<GetDailyWaterConsumptionSummaryUseCase>().invoke(any() as SummaryRequest.LastSummaries) }.returns(
            lastSummaries
        )
        every { get<GetHistoryChartDataUseCase>().invoke(any(), any()) }.returns(historyChart)
        view = mockk(relaxed = true)
        presenter = get()
        presenter.attachView(view)
    }

    @Test
    fun `on summarized data return chart and sections for each summary`() = runTest(testDispatcher) {
        lastSummaries.emit(listOf(mockk(relaxed = true)))
        historyChart.emit(mockk())

        advanceUntilIdle()

        verify(exactly = 1) {
            view.updateHistorySections(any())
        }
    }



    @Test
    fun `on empty data return empty sections`() = runTest(testDispatcher) {
        lastSummaries.emit(listOf())
        historyChart.emit(mockk())

        advanceUntilIdle()

        verify(exactly = 1) {
            view.updateHistorySections(listOf())
        }
    }


    @Test
    fun `on select chart option call usecase`() = runTest(testDispatcher) {
        advanceUntilIdle()

        presenter.onSelectChartOption(ChartOption.Month)

        advanceUntilIdle()

        coVerify(exactly = 1) {
            get<GetHistoryChartDataUseCase>().invoke(ChartOption.Month, any())
        }
    }

    @Test
    fun `on delete entry call usecase`() = runTest(testDispatcher) {
        presenter.onDeleteAction(mockk(relaxed = true))

        advanceUntilIdle()

        coVerify(exactly = 1) {
            get<DeleteConsumedWaterUseCase>().invoke(any())
        }

    }

    @Test
    fun `on prev chart period`() = runTest(testDispatcher) {
        advanceUntilIdle()

        presenter.onPreviousChartPeriod()

        advanceUntilIdle()

        coVerify(exactly = 2) {
            get<GetHistoryChartDataUseCase>().invoke(any(), any())
        }
    }

    @Test
    fun `on next chart period`() = runTest(testDispatcher) {
        advanceUntilIdle()

        presenter.onNextChartPeriod()

        advanceUntilIdle()

        coVerify(exactly = 2) {
            get<GetHistoryChartDataUseCase>().invoke(any(), any())
        }
    }
}