package com.github.lucascalheiros.waterreminder.feature.home.ui.addwatersource

import androidx.lifecycle.SavedStateHandle
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.models.DefaultAddWaterSourceInfo
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.CreateWaterSourceUseCase
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.GetDefaultAddWaterSourceInfoUseCase
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.GetWaterSourceTypeUseCase
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.requests.CreateWaterSourceRequest
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
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.get
import org.koin.test.inject
import org.koin.test.mock.MockProviderRule
import org.koin.test.mock.declareMock

@OptIn(ExperimentalCoroutinesApi::class)
class AddWaterSourcePresenterTest : KoinTest {

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        printLogger()
        modules(testModule + dispatchersQualifierModule)
    }

    private val testModule = module {
        viewModel {
            AddWaterSourcePresenter(
                coroutineDispatcher = testDispatcher,
                get(),
                get(),
                get(),
                get(),
                get(),
            )
        }
        single { mockk<GetWaterSourceTypeUseCase>(relaxed = true) } bind GetWaterSourceTypeUseCase::class
        single { mockk<GetDefaultAddWaterSourceInfoUseCase>(relaxed = true) } bind GetDefaultAddWaterSourceInfoUseCase::class
        single { mockk<GetVolumeUnitUseCase>(relaxed = true) } bind GetVolumeUnitUseCase::class
        single { mockk<CreateWaterSourceUseCase>(relaxed = true) } bind CreateWaterSourceUseCase::class
        factory { SavedStateHandle() }
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
        coEvery { get<GetVolumeUnitUseCase>().single() } returns MeasureSystemVolumeUnit.ML

        coEvery { get<GetDefaultAddWaterSourceInfoUseCase>().invoke() } returns DefaultAddWaterSourceInfo(
            MeasureSystemVolume.Companion.create(250.0, MeasureSystemVolumeUnit.ML),
            mockk()
        )

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
    fun `default data should be set on initialization and always on attach-dettached`() =
        runTest(testDispatcher) {
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
    fun `default data should be set even if view is dettached before initialize`() =
        runTest(testDispatcher) {
            val data = DefaultAddWaterSourceInfo(
                volumeValue1,
                waterSourceType1,
            )

            coEvery { declareMock<GetDefaultAddWaterSourceInfoUseCase>().invoke() } returns data

            val presenter: AddWaterSourcePresenter by inject()

            presenter.attachView(view)

            presenter.detachView()

            presenter.initialize()

            presenter.attachView(view)

            advanceUntilIdle()

            verify {
                view.setSelectedWaterSourceType(waterSourceType1)
                view.setSelectedVolume(volumeValue1)
            }
        }

    @Test
    fun `when creating water source passed volume value should be set`() = runTest(testDispatcher) {
        val presenter: AddWaterSourcePresenter by inject()

        presenter.attachView(view)

        presenter.initialize()

        presenter.onVolumeSelected(199.0)

        presenter.onWaterSourceTypeSelected(waterSourceType2)

        presenter.onConfirmClick()

        advanceUntilIdle()

        coVerify {
            get<CreateWaterSourceUseCase>().invoke(
                CreateWaterSourceRequest(
                    MeasureSystemVolume.create(199.0, MeasureSystemVolumeUnit.ML),
                    waterSourceType2
                )
            )
        }
    }

    @Test
    fun `volume selected should update ui`() = runTest(testDispatcher) {
        view = mockk<AddWaterSourceContract.View>(relaxed = true)

        val presenter: AddWaterSourcePresenter = get()

        presenter.attachView(view)

        presenter.initialize()

        presenter.onVolumeSelected(100.0)

        advanceUntilIdle()

        verify {
            view.setSelectedVolume(
                MeasureSystemVolume.Companion.create(
                    100.0,
                    MeasureSystemVolumeUnit.ML
                )
            )
        }
    }

    @Test
    fun `on click volume should show input`() = runTest(testDispatcher) {
        presenter.onVolumeOptionClick()

        advanceUntilIdle()

        verify(exactly = 1) {
            view.showVolumeInputDialog(any())
        }
    }

    @Test
    fun `on click water source type should show input`() = runTest(testDispatcher) {
        presenter.onSelectWaterSourceTypeOptionClick()

        advanceUntilIdle()

        verify(exactly = 1) {
            view.showSelectWaterSourceDialog(any())
        }
    }

    @Test
    fun `on create request failure should show error toast once and dismiss`() =
        runTest(testDispatcher) {
            val mock = declareMock<CreateWaterSourceUseCase>()

            view = mockk<AddWaterSourceContract.View>(relaxed = true)

            coEvery { mock.invoke(any()) } throws Exception("")

            val presenter: AddWaterSourcePresenter by inject()

            presenter.attachView(view)

            presenter.initialize()

            advanceUntilIdle()

            presenter.onConfirmClick()

            presenter.detachView()

            advanceUntilIdle()

            presenter.attachView(view)

            advanceUntilIdle()

            coVerify(exactly = 1) {
                view.showOperationErrorToast(any())
                view.showOperationErrorToast(AddWaterSourceContract.ErrorEvent.SaveFailed)
                view.dismissBottomSheet()
            }
        }

    @Test
    fun `on load data failure should show error toast once and dismiss`() =
        runTest(testDispatcher) {
            val mock = declareMock<GetDefaultAddWaterSourceInfoUseCase>()

            view = mockk<AddWaterSourceContract.View>(relaxed = true)

            coEvery { mock.invoke() } throws Exception("")

            val presenter: AddWaterSourcePresenter by inject()

            presenter.attachView(view)

            presenter.initialize()

            advanceUntilIdle()

            coVerify(exactly = 1) {
                view.showOperationErrorToast(any())
                view.showOperationErrorToast(AddWaterSourceContract.ErrorEvent.DataLoadingFailed)
                view.dismissBottomSheet()
            }
        }

}
