package com.github.lucascalheiros.waterreminder.feature.settings.ui.unitselector

import androidx.lifecycle.SavedStateHandle
import com.github.lucascalheiros.waterreminder.feature.home.test.MainDispatcherRule
import com.github.lucascalheiros.waterreminder.feature.home.test.dispatchersQualifierModule
import com.github.lucascalheiros.waterreminder.feature.home.test.testDispatcher
import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemTemperatureUnit
import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemVolumeUnit
import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemWeightUnit
import com.github.lucascalheiros.waterreminder.measuresystem.domain.usecases.GetTemperatureUnitUseCase
import com.github.lucascalheiros.waterreminder.measuresystem.domain.usecases.GetVolumeUnitUseCase
import com.github.lucascalheiros.waterreminder.measuresystem.domain.usecases.GetWeightUnitUseCase
import com.github.lucascalheiros.waterreminder.measuresystem.domain.usecases.SetTemperatureUnitUseCase
import com.github.lucascalheiros.waterreminder.measuresystem.domain.usecases.SetVolumeUnitUseCase
import com.github.lucascalheiros.waterreminder.measuresystem.domain.usecases.SetWeightUnitUseCase
import io.mockk.mockk
import io.mockk.mockkClass
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
class UnitSelectorPresenterTest: KoinTest {
    @get:Rule
    val koinTestRule = KoinTestRule.create {
        printLogger()
        modules(testModule + dispatchersQualifierModule)
    }

    private val testModule = module {
        viewModel {
            UnitSelectorPresenter(
                mainDispatcher = testDispatcher,
                get(),
                get(),
                get(),
                get(),
                get(),
                get(),
            )
        }
        single { mockk<GetVolumeUnitUseCase>(relaxed = true) }
        single { mockk<GetWeightUnitUseCase>(relaxed = true) }
        single { mockk<GetTemperatureUnitUseCase>(relaxed = true) }
        single { mockk<SetVolumeUnitUseCase>(relaxed = true) }
        single { mockk<SetWeightUnitUseCase>(relaxed = true) }
        single { mockk<SetTemperatureUnitUseCase>(relaxed = true) }
        factory { SavedStateHandle() }
    }

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @get:Rule
    val mockProvider = MockProviderRule.create { clazz ->
        mockkClass(clazz)
    }

    private lateinit var presenter: UnitSelectorPresenter

    private lateinit var view: UnitSelectorContract.View

    @Before
    fun setup() {
        view = mockk(relaxed = true)
        presenter = get()
        presenter.attachView(view)
    }

    @Test
    fun `loadData should update UI with correct units`() = runTest(testDispatcher) {
        presenter.loadData()
        advanceUntilIdle()
        verify(exactly = 1) { view.setVolumeUnit(any()) }
        verify(exactly = 1) { view.setTemperatureUnit(any()) }
        verify(exactly = 1) { view.setWeightUnit(any()) }
    }

    @Test
    fun `onChangeVolumeUnit should update UI`() = runTest(testDispatcher) {
        val volumeUnit = MeasureSystemVolumeUnit.ML

        presenter.onChangeVolumeUnit(volumeUnit)
        advanceUntilIdle()

        verify(exactly = 1) { view.setVolumeUnit(volumeUnit) }
    }

    @Test
    fun `onChangeTemperatureUnit should update UI`() = runTest(testDispatcher) {
        val temperatureUnit = MeasureSystemTemperatureUnit.Celsius

        presenter.onChangeTemperatureUnit(temperatureUnit)
        advanceUntilIdle()

        verify(exactly = 1) { view.setTemperatureUnit(temperatureUnit) }
    }

    @Test
    fun `onChangeWeightUnit should update UI`() = runTest(testDispatcher) {
        val weightUnit = MeasureSystemWeightUnit.KILOGRAMS

        presenter.onChangeWeightUnit(weightUnit)
        advanceUntilIdle()

        verify(exactly = 1) { view.setWeightUnit(weightUnit) }
    }
}