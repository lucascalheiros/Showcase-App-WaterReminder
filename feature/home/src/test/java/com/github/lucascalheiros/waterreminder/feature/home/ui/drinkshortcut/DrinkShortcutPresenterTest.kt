package com.github.lucascalheiros.waterreminder.feature.home.ui.drinkshortcut

import androidx.lifecycle.SavedStateHandle
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.GetDefaultVolumeShortcutsUseCase
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.GetWaterSourceTypeUseCase
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.RegisterConsumedWaterUseCase
import com.github.lucascalheiros.waterreminder.feature.home.test.MainDispatcherRule
import com.github.lucascalheiros.waterreminder.feature.home.test.TestLoggingHandler
import com.github.lucascalheiros.waterreminder.feature.home.test.defaultVolumeShortcuts1
import com.github.lucascalheiros.waterreminder.feature.home.test.dispatchersQualifierModule
import com.github.lucascalheiros.waterreminder.feature.home.test.testDispatcher
import com.github.lucascalheiros.waterreminder.feature.home.test.waterSourceType2
import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemVolume
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.mockkClass
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
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

@OptIn(ExperimentalCoroutinesApi::class)
class DrinkShortcutPresenterTest: KoinTest {

    init {
        TestLoggingHandler.setup()
    }

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        printLogger()
        modules(
            testModule + dispatchersQualifierModule
        )
    }

    private val testModule = module {
        viewModel {
            DrinkShortcutPresenter(
                coroutineDispatcher = testDispatcher,
                get(),
                get(),
                get(),
                get(),
            )
        }
        single { mockk<GetWaterSourceTypeUseCase>(relaxed = true) }
        single { mockk<RegisterConsumedWaterUseCase>(relaxed = true) }
        single { mockk<GetDefaultVolumeShortcutsUseCase>(relaxed = true) }
        factory { SavedStateHandle() }
    }

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @get:Rule
    val mockProvider = MockProviderRule.create { clazz ->
        mockkClass(clazz)
    }

    private lateinit var presenter: DrinkShortcutPresenter

    private lateinit var view: DrinkShortcutContract.View

    @Test
    fun `should set view states on initialization`() = runTest(testDispatcher) {
        coEvery { get<GetWaterSourceTypeUseCase>().invoke(waterSourceType2.waterSourceTypeId) } returns waterSourceType2

        coEvery { get<GetDefaultVolumeShortcutsUseCase>().invoke() } returns defaultVolumeShortcuts1

        view = mockk<DrinkShortcutContract.View>(relaxed = true)

        presenter = get()

        presenter.attachView(view)

        presenter.initialize(waterSourceType2.waterSourceTypeId)

        advanceUntilIdle()

        verify {
            view.setSelectedVolume(defaultVolumeShortcuts1.medium)
            view.setVolumeShortcuts(defaultVolumeShortcuts1)
        }
    }

    @Test
    fun `should dismiss on cancel`() = runTest(testDispatcher) {
        coEvery { get<GetWaterSourceTypeUseCase>().invoke(waterSourceType2.waterSourceTypeId) } returns waterSourceType2

        coEvery { get<GetDefaultVolumeShortcutsUseCase>().invoke() } returns defaultVolumeShortcuts1

        view = mockk<DrinkShortcutContract.View>(relaxed = true)

        presenter = get()

        presenter.attachView(view)

        presenter.initialize(waterSourceType2.waterSourceTypeId)

        advanceUntilIdle()

        presenter.onCancelClick()

        advanceUntilIdle()

        verify(exactly = 1) {
            view.dismissBottomSheet()
        }
    }

    @Test
    fun `confirm without change will register medium shortcut and dismiss`() = runTest(testDispatcher) {
        coEvery { get<GetWaterSourceTypeUseCase>().invoke(waterSourceType2.waterSourceTypeId) } returns waterSourceType2

        coEvery { get<GetDefaultVolumeShortcutsUseCase>().invoke() } returns defaultVolumeShortcuts1

        view = mockk<DrinkShortcutContract.View>(relaxed = true)

        presenter = get()

        presenter.attachView(view)

        presenter.initialize(waterSourceType2.waterSourceTypeId)

        advanceUntilIdle()

        presenter.onConfirmClick()

        advanceUntilIdle()

        coVerify(exactly = 1) {
            get<RegisterConsumedWaterUseCase>().invoke(defaultVolumeShortcuts1.medium, waterSourceType2)
            view.dismissBottomSheet()
        }
    }

    @Test
    fun `confirm without change will register selected volume and dismiss`() = runTest(testDispatcher) {
        coEvery { get<GetWaterSourceTypeUseCase>().invoke(waterSourceType2.waterSourceTypeId) } returns waterSourceType2

        coEvery { get<GetDefaultVolumeShortcutsUseCase>().invoke() } returns defaultVolumeShortcuts1

        view = mockk<DrinkShortcutContract.View>(relaxed = true)

        presenter = get()

        presenter.attachView(view)

        presenter.initialize(waterSourceType2.waterSourceTypeId)

        advanceUntilIdle()

        presenter.onVolumeSelected(55.0)

        presenter.onConfirmClick()

        advanceUntilIdle()

        val newVolume = MeasureSystemVolume.create(55.0, defaultVolumeShortcuts1.medium.volumeUnit())

        coVerify(exactly = 1) {
            get<RegisterConsumedWaterUseCase>().invoke(newVolume, waterSourceType2)
            view.dismissBottomSheet()
        }
    }

    @Test
    fun onVolumeSelected()  = runTest(testDispatcher) {
        coEvery { get<GetWaterSourceTypeUseCase>().invoke(waterSourceType2.waterSourceTypeId) } returns waterSourceType2

        coEvery { get<GetDefaultVolumeShortcutsUseCase>().invoke() } returns defaultVolumeShortcuts1

        view = mockk<DrinkShortcutContract.View>(relaxed = true)

        presenter = get()

        presenter.attachView(view)

        presenter.initialize(waterSourceType2.waterSourceTypeId)

        advanceUntilIdle()

        presenter.onVolumeSelected(55.0)

        presenter.onConfirmClick()

        advanceUntilIdle()

        val newVolume = MeasureSystemVolume.create(55.0, defaultVolumeShortcuts1.medium.volumeUnit())

        verify(exactly = 1) {
            view.setSelectedVolume(newVolume)
        }
    }
}