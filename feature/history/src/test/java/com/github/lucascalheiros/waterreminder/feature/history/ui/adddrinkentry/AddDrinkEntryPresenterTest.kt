package com.github.lucascalheiros.waterreminder.feature.history.ui.adddrinkentry

import androidx.lifecycle.SavedStateHandle
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.models.DefaultAddWaterSourceInfo
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.models.WaterSourceType
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.GetDefaultAddWaterSourceInfoUseCase
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.GetWaterSourceTypeUseCase
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.RegisterConsumedWaterUseCase
import com.github.lucascalheiros.waterreminder.feature.home.test.MainDispatcherRule
import com.github.lucascalheiros.waterreminder.feature.home.test.dispatchersQualifierModule
import com.github.lucascalheiros.waterreminder.feature.home.test.testDispatcher
import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemVolume
import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemVolumeUnit
import com.github.lucascalheiros.waterreminder.measuresystem.domain.usecases.GetVolumeUnitUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.mockkClass
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
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
class AddDrinkEntryPresenterTest : KoinTest {
    @get:Rule
    val koinTestRule = KoinTestRule.create {
        printLogger()
        modules(testModule + dispatchersQualifierModule)
    }

    private val testModule = module {
        viewModel {
            AddDrinkEntryPresenter(
                coroutineDispatcher = testDispatcher,
                get(),
                get(),
                get(),
                get(),
                get(),
            )
        }
        single { mockk<GetWaterSourceTypeUseCase>(relaxed = true) }
        single { mockk<GetDefaultAddWaterSourceInfoUseCase>(relaxed = true) }
        single { mockk<GetVolumeUnitUseCase>(relaxed = true) }
        single { mockk<RegisterConsumedWaterUseCase>(relaxed = true) }
        factory { SavedStateHandle() }
    }

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @get:Rule
    val mockProvider = MockProviderRule.create { clazz ->
        mockkClass(clazz)
    }

    private lateinit var presenter: AddDrinkEntryPresenter

    private lateinit var view: AddDrinkEntryContract.View

    private lateinit var defaultData: DefaultAddWaterSourceInfo

    @Before
    fun setup() {
        defaultData = DefaultAddWaterSourceInfo(
            MeasureSystemVolume.Companion.create(100.0, MeasureSystemVolumeUnit.ML),
            WaterSourceType(1, "Water", 0, 0, 1f, false)
        )
        coEvery { get<GetDefaultAddWaterSourceInfoUseCase>().invoke() }.returns(defaultData)

        view = mockk(relaxed = true)
        presenter = get()
        presenter.attachView(view)
    }

    @Test
    fun `on initialization failure show toast and dismiss`() = runTest(testDispatcher) {
        coEvery { get<GetDefaultAddWaterSourceInfoUseCase>().invoke() }.throws(Exception())

        presenter.initialize()

        advanceUntilIdle()

        verify(exactly = 1) {
            view.showOperationErrorToast(AddDrinkEntryContract.ErrorEvent.DataLoadingFailed)
            view.dismissBottomSheet()
        }
    }

    @Test
    fun `on initialization success update ui`() = runTest(testDispatcher) {
        presenter.initialize()

        advanceUntilIdle()

        verify(exactly = 1) {
            view.setSelectedVolume(defaultData.volume)
            view.setSelectedWaterSourceType(defaultData.waterSourceType)
        }
    }

    @Test
    fun `on volume click show dialog`() = runTest(testDispatcher) {
        coEvery { get<GetVolumeUnitUseCase>().single() }.returns(MeasureSystemVolumeUnit.ML)
        presenter.initialize()
        presenter.onVolumeOptionClick()

        verify(exactly = 1) {
            view.showVolumeInputDialog(MeasureSystemVolumeUnit.ML)
        }
    }

    @Test
    fun `on date click show dialog`() = runTest(testDispatcher) {
        presenter.initialize()
        advanceUntilIdle()
        presenter.onDateTimeClick()

        advanceUntilIdle()

        verify(exactly = 1) {
            view.showDateTimeInputDialog(any())
        }
    }

    @Test
    fun `on water source click show dialog`() = runTest(testDispatcher) {
        coEvery { get<GetWaterSourceTypeUseCase>().single() }.returns(listOf())

        presenter.initialize()
        presenter.onSelectWaterSourceTypeOptionClick()

        advanceUntilIdle()

        verify(exactly = 1) {
            view.showSelectWaterSourceDialog(listOf())
        }
    }

    @Test
    fun `on cancel dismiss`() = runTest(testDispatcher) {
        presenter.initialize()
        presenter.onCancelClick()

        advanceUntilIdle()

        verify(exactly = 1) {
            view.dismissBottomSheet()
        }
    }

    @Test
    fun `on confirm save and dismiss`() = runTest(testDispatcher) {
        presenter.initialize()

        advanceUntilIdle()

        presenter.onConfirmClick()

        advanceUntilIdle()

        coVerify(exactly = 1) {
            get<RegisterConsumedWaterUseCase>().invoke(any(), any(), any())
        }
        verify(exactly = 1) {
            view.dismissBottomSheet()
        }
    }

    @Test
    fun `on set volume update ui`() = runTest(testDispatcher) {
        val volume = MeasureSystemVolume.Companion.create(1000.0, MeasureSystemVolumeUnit.ML)

        presenter.initialize()

        advanceUntilIdle()

        presenter.onVolumeSelected(volume.intrinsicValue())

        advanceUntilIdle()

        verify(exactly = 1) {
            view.setSelectedVolume(volume)
        }
    }

    @Test
    fun `on set date update ui`() = runTest(testDispatcher) {
        val date = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())

        presenter.initialize()

        advanceUntilIdle()

        presenter.onDateTimeChange(date)

        advanceUntilIdle()

        verify(exactly = 1) {
            view.setSelectedDateTime(date)
        }
    }

    @Test
    fun `on set water source update ui`() = runTest(testDispatcher) {
        val waterSourceType = WaterSourceType(2, "Water", 0, 0, 1f, false)

        presenter.initialize()

        advanceUntilIdle()

        presenter.onWaterSourceTypeSelected(waterSourceType)

        advanceUntilIdle()

        verify(exactly = 1) {
            view.setSelectedWaterSourceType(waterSourceType)
        }
    }
}

