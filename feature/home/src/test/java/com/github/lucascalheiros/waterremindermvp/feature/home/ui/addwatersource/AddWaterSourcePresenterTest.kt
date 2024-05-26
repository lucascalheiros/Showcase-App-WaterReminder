package com.github.lucascalheiros.waterremindermvp.feature.home.ui.addwatersource

import com.github.lucascalheiros.waterremindermvp.common.measuresystem.MeasureSystemUnit
import com.github.lucascalheiros.waterremindermvp.common.measuresystem.MeasureSystemVolume
import com.github.lucascalheiros.waterremindermvp.common.measuresystem.MeasureSystemVolumeUnit
import com.github.lucascalheiros.waterremindermvp.domain.watermanagement.domain.models.DefaultAddWaterSourceInfo
import com.github.lucascalheiros.waterremindermvp.domain.watermanagement.domain.models.WaterSource
import com.github.lucascalheiros.waterremindermvp.domain.watermanagement.domain.usecases.CreateWaterSourceUseCase
import com.github.lucascalheiros.waterremindermvp.domain.watermanagement.domain.usecases.GetCurrentMeasureSystemUnitUseCase
import com.github.lucascalheiros.waterremindermvp.domain.watermanagement.domain.usecases.GetDefaultAddWaterSourceInfoUseCase
import com.github.lucascalheiros.waterremindermvp.domain.watermanagement.domain.usecases.requests.AsyncRequest
import com.github.lucascalheiros.waterremindermvp.feature.home.di.homeModule
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.mockkClass
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.get
import org.koin.test.inject
import org.koin.test.mock.MockProviderRule
import org.koin.test.mock.declareMock

@OptIn(ExperimentalCoroutinesApi::class)
class AddWaterSourcePresenterTest: KoinTest {

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        printLogger()
        modules(homeModule + dispatchersQualifierModule)
    }

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @get:Rule
    val mockProvider = MockProviderRule.create { clazz ->
        mockkClass(clazz)
    }

    private lateinit var presenter: AddWaterSourcePresenter

    private lateinit var view: AddWaterSourceContract.View

    @Before
    fun attachViewToPresenterAndInitialize() {
        coEvery { declareMock<GetCurrentMeasureSystemUnitUseCase>().invoke(AsyncRequest.Single) } returns MeasureSystemUnit.SI

        view = mockk<AddWaterSourceContract.View>(relaxed = true)

        presenter = get()

        presenter.attachView(view)

        presenter.initialize()
    }

    @Test
    fun `should set view states on initialization`() = runTest(testDispatcher) {

        advanceUntilIdle()

        verify {
            view.setSelectedWaterSourceType(any())
            view.setSelectedVolume(any())
        }
    }


    @Test
    fun `cancel should dismiss bottom sheet`() = runTest(testDispatcher) {
        presenter.onCancelClick()

        advanceUntilIdle()

        verify {
            view.dismissBottomSheet()
        }
    }

    @Test
    fun `confirm should dismiss sheet`() = runTest(testDispatcher) {
        presenter.onConfirmClick()

        advanceUntilIdle()

        verify {
            view.dismissBottomSheet()
        }
    }

    @Test
    fun `confirm failure should emit error and dismiss`() = runTest(testDispatcher) {
        val mock = declareMock<CreateWaterSourceUseCase>()

        coEvery { mock.invoke(any()) } throws Exception()

        val presenter: AddWaterSourcePresenter by inject()

        presenter.attachView(view)

        presenter.onConfirmClick()

        advanceUntilIdle()

        verify {
            view.showOperationErrorToast(AddWaterSourceContract.ErrorEvent.SaveFailed)
            view.dismissBottomSheet()
        }
    }


    @Test
    fun `default data should be set on initialization and always on attach-dettached`() = runTest(testDispatcher) {
        val mock = declareMock<GetDefaultAddWaterSourceInfoUseCase>()

        val data = DefaultAddWaterSourceInfo(
            mockk(),
            mockk(),
        )

        coEvery { mock.invoke() } returns data

        val presenter: AddWaterSourcePresenter by inject()

        val view = mockk<AddWaterSourceContract.View>(relaxed = true)

        presenter.attachView(view)

        presenter.initialize()

        advanceUntilIdle()

        presenter.detachView()

        presenter.attachView(view)

        advanceUntilIdle()

        verify(exactly = 2) {
            view.setSelectedWaterSourceType(any())
            view.setSelectedVolume(any())
        }
    }


    @Test
    fun `default data should be set even if view is dettached before initialize`() = runTest(testDispatcher) {
        val data = DefaultAddWaterSourceInfo(
            defaultVolumeValue,
            defaultWaterSourceType,
        )

        coEvery { declareMock<GetDefaultAddWaterSourceInfoUseCase>().invoke() } returns data

        val presenter: AddWaterSourcePresenter by inject()

        presenter.attachView(view)

        presenter.detachView()

        presenter.initialize()

        presenter.attachView(view)

        advanceUntilIdle()

        verify {
            view.setSelectedWaterSourceType(defaultWaterSourceType)
            view.setSelectedVolume(defaultVolumeValue)
        }
    }

    @Test
    fun `when creating water source passed volume value should be set`() = runTest(testDispatcher) {
        val mock = declareMock<CreateWaterSourceUseCase>()

        val presenter: AddWaterSourcePresenter by inject()

        presenter.attachView(view)

        presenter.initialize()

        presenter.onVolumeSelected(199.0)

        presenter.onConfirmClick()

        advanceUntilIdle()

        coVerify {
            mock.invoke(WaterSource(-1, MeasureSystemVolume.Companion.create(199.0, MeasureSystemVolumeUnit.ML), defaultWaterSourceType))
        }
    }

    @Test
    fun `volume selected should update ui`() = runTest(testDispatcher) {
        val mock = declareMock<GetCurrentMeasureSystemUnitUseCase>()

        coEvery { mock.invoke(AsyncRequest.Single) } returns MeasureSystemUnit.SI

        val presenter: AddWaterSourcePresenter by inject()

        presenter.attachView(view)

        presenter.initialize()

        presenter.onVolumeSelected(100.0)

        advanceUntilIdle()

        verify {
            view.setSelectedVolume(MeasureSystemVolume.Companion.create(100.0, MeasureSystemUnit.SI))
        }
    }
}
