package com.github.lucascalheiros.waterreminder.feature.home.ui.home

import androidx.lifecycle.SavedStateHandle
import com.github.lucascalheiros.waterreminder.domain.home.domain.usecases.GetSortedDrinkUseCase
import com.github.lucascalheiros.waterreminder.domain.home.domain.usecases.GetSortedWaterSourceUseCase
import com.github.lucascalheiros.waterreminder.domain.home.domain.usecases.SetDrinkSortPositionUseCase
import com.github.lucascalheiros.waterreminder.domain.home.domain.usecases.SetWaterSourceSortPositionUseCase
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.models.WaterSource
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.models.WaterSourceType
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.DeleteWaterSourceTypeUseCase
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.DeleteWaterSourceUseCase
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.GetTodayWaterConsumptionSummaryUseCase
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.RegisterConsumedWaterUseCase
import com.github.lucascalheiros.waterreminder.feature.home.test.MainDispatcherRule
import com.github.lucascalheiros.waterreminder.feature.home.test.dispatchersQualifierModule
import com.github.lucascalheiros.waterreminder.feature.home.test.summary1
import com.github.lucascalheiros.waterreminder.feature.home.test.summary2
import com.github.lucascalheiros.waterreminder.feature.home.test.testDispatcher
import com.github.lucascalheiros.waterreminder.feature.home.test.volumeValue1
import com.github.lucascalheiros.waterreminder.feature.home.test.waterSourceType1
import com.github.lucascalheiros.waterreminder.feature.home.ui.home.adapters.drinkchips.DrinkItems
import com.github.lucascalheiros.waterreminder.feature.home.ui.home.adapters.watersource.WaterSourceCard
import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemVolumeUnit
import com.github.lucascalheiros.waterreminder.measuresystem.domain.usecases.GetVolumeUnitUseCase
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.mockkClass
import io.mockk.slot
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.get
import org.koin.test.mock.MockProviderRule
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class HomePresenterTest : KoinTest {

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        printLogger()
        modules(
            testModule + dispatchersQualifierModule
        )
    }

    private val testModule = module {
        viewModel {
            HomePresenter(
                mainDispatcher = testDispatcher,
                get(),
                get(),
                get(),
                get(),
                get(),
                get(),
                get(),
                get(),
                get(),
            )
        }
        single { mockk<GetTodayWaterConsumptionSummaryUseCase>(relaxed = true) }
        single { mockk<RegisterConsumedWaterUseCase>(relaxed = true) }
        single { mockk<DeleteWaterSourceUseCase>(relaxed = true) }
        single { mockk<GetVolumeUnitUseCase>(relaxed = true) }
        single { mockk<DeleteWaterSourceTypeUseCase>(relaxed = true) }
        single { mockk<GetSortedDrinkUseCase>(relaxed = true) }
        single { mockk<GetSortedWaterSourceUseCase>(relaxed = true) }
        single { mockk<SetDrinkSortPositionUseCase>(relaxed = true) }
        single { mockk<SetWaterSourceSortPositionUseCase>(relaxed = true) }
        factory { SavedStateHandle() }
    }

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @get:Rule
    val mockProvider = MockProviderRule.create { clazz ->
        mockkClass(clazz)
    }

    private lateinit var presenter: HomePresenter

    private lateinit var view: HomeContract.View

    @Test
    fun `should set view states on initialization`() = runTest(testDispatcher) {
        coEvery { get<GetSortedWaterSourceUseCase>().invoke() } returns flowOf(listOf())

        coEvery { get<GetSortedDrinkUseCase>().invoke() } returns flowOf(listOf())

        coEvery { get<GetTodayWaterConsumptionSummaryUseCase>().invoke() } returns flowOf(mockk(relaxed = true))

        view = mockk<HomeContract.View>(relaxed = true)

        presenter = get()

        presenter.attachView(view)

        advanceUntilIdle()

        verify {
            view.setDrinkList(any())
            view.setWaterSourceList(any())
            view.setTodayConsumptionSummary(any())
        }
    }


    @Test
    fun `the last items from drink and cup cards should be the add card`() = runTest(testDispatcher) {
        coEvery { get<GetSortedWaterSourceUseCase>().invoke() } returns flowOf(listOf(mockk()))
        coEvery { get<GetSortedDrinkUseCase>().invoke() } returns flowOf(listOf(mockk()))

        val drinkSlot = slot<List<DrinkItems>>()
        val cupSlot = slot<List<WaterSourceCard>>()

        view = mockk<HomeContract.View>(relaxed = true)

        every { view.setDrinkList(capture(drinkSlot)) } just Runs
        every { view.setWaterSourceList(capture(cupSlot)) } just Runs

        presenter = get()

        presenter.attachView(view)

        advanceUntilIdle()

        assertEquals(2, drinkSlot.captured.size)
        assertEquals(2, cupSlot.captured.size)
        assertEquals(DrinkItems.AddItem, drinkSlot.captured.last())
        assertEquals(WaterSourceCard.AddItem, cupSlot.captured.last())
    }


    @Test
    fun `add drink should trigger respective ui event`() = runTest(testDispatcher) {
        view = mockk<HomeContract.View>(relaxed = true)

        presenter = get()

        presenter.attachView(view)

        presenter.onAddDrinkClick()

        advanceUntilIdle()

        verify(exactly = 1) {
            view.sendUIEvent(HomeContract.ViewUIEvents.OpenAddDrink)
        }
    }

    @Test
    fun `add cup should trigger respective ui event`() = runTest(testDispatcher) {
        view = mockk<HomeContract.View>(relaxed = true)

        presenter = get()

        presenter.attachView(view)

        presenter.onAddWaterSourceClick()

        advanceUntilIdle()

        verify(exactly = 1) {
            view.sendUIEvent(HomeContract.ViewUIEvents.OpenAddWaterSource)
        }
    }

    @Test
    fun `open drink shortcut should trigger respective ui event`() = runTest(testDispatcher) {
        coEvery { get<GetVolumeUnitUseCase>().single() } returns MeasureSystemVolumeUnit.ML

        view = mockk<HomeContract.View>(relaxed = true)

        presenter = get()

        presenter.attachView(view)

        presenter.onDrinkClick(waterSourceType1)

        advanceUntilIdle()

        verify(exactly = 1) {
            view.sendUIEvent(HomeContract.ViewUIEvents.OpenDrinkShortcut(waterSourceType1, MeasureSystemVolumeUnit.ML))
        }
    }

    @Test
    fun `update on cuo flow should be reflected on ui`() = runTest(testDispatcher) {
        val testFlow = MutableStateFlow<List<WaterSource>>(listOf())

        coEvery { get<GetSortedWaterSourceUseCase>().invoke() } returns testFlow

        view = mockk<HomeContract.View>(relaxed = true)

        presenter = get()

        presenter.attachView(view)

        advanceUntilIdle()

        verify {
            view.setWaterSourceList(listOf(WaterSourceCard.AddItem))
        }

        testFlow.value = listOf(WaterSource(0, volumeValue1, waterSourceType1))

        advanceUntilIdle()

        verify {
            view.setWaterSourceList(listOf(WaterSource(0, volumeValue1, waterSourceType1)).map { WaterSourceCard.ConsumptionItem(it) } + listOf(WaterSourceCard.AddItem) )
        }
    }

    @Test
    fun `update on drink flow should be reflected on ui`() = runTest(testDispatcher) {
        val testFlow = MutableStateFlow<List<WaterSourceType>>(listOf())

        coEvery { get<GetSortedDrinkUseCase>().invoke() } returns testFlow

        view = mockk<HomeContract.View>(relaxed = true)

        presenter = get()

        presenter.attachView(view)

        advanceUntilIdle()

        verify {
            view.setDrinkList(listOf(DrinkItems.AddItem))
        }

        testFlow.value = listOf(waterSourceType1)

        advanceUntilIdle()

        verify {
            view.setDrinkList(listOf(waterSourceType1).map { DrinkItems.OptionItem(it) } + listOf(DrinkItems.AddItem) )
        }
    }

    @Test
    fun `update on summary flow should be reflected on ui`() = runTest(testDispatcher) {
        val testFlow = MutableStateFlow(summary1)

        coEvery { get<GetTodayWaterConsumptionSummaryUseCase>().invoke() } returns testFlow

        view = mockk<HomeContract.View>(relaxed = true)

        presenter = get()

        presenter.attachView(view)

        advanceUntilIdle()

        verify {
            view.setTodayConsumptionSummary(summary1)
        }

        testFlow.value = summary2

        advanceUntilIdle()

        verify {
            view.setTodayConsumptionSummary(summary2)
        }
    }

}