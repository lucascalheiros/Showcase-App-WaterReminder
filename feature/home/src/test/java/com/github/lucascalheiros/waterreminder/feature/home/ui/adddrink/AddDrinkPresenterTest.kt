package com.github.lucascalheiros.waterreminder.feature.home.ui.adddrink

import androidx.lifecycle.SavedStateHandle
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.CreateWaterSourceTypeUseCase
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.GetDefaultAddDrinkInfoUseCase
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.requests.CreateWaterSourceTypeRequest
import com.github.lucascalheiros.waterreminder.feature.home.test.MainDispatcherRule
import com.github.lucascalheiros.waterreminder.feature.home.test.defaultDrinkInfo1
import com.github.lucascalheiros.waterreminder.feature.home.test.dispatchersQualifierModule
import com.github.lucascalheiros.waterreminder.feature.home.test.testDispatcher
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
class AddDrinkPresenterTest : KoinTest {

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        printLogger()
        modules(testModule + dispatchersQualifierModule)
    }

    private val testModule = module {
        viewModel {
            AddDrinkPresenter(
                coroutineDispatcher = testDispatcher,
                get(),
                get(),
            )
        }
        single { mockk<CreateWaterSourceTypeUseCase>(relaxed = true) }
        single { mockk<GetDefaultAddDrinkInfoUseCase>(relaxed = true) }
        factory { SavedStateHandle() }
    }

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @get:Rule
    val mockProvider = MockProviderRule.create { clazz ->
        mockkClass(clazz)
    }

    private lateinit var presenter: AddDrinkPresenter

    private lateinit var view: AddDrinkContract.View

    @Test
    fun `should set view states on initialization`() = runTest(testDispatcher) {
        coEvery { get<GetDefaultAddDrinkInfoUseCase>().invoke() } returns defaultDrinkInfo1

        view = mockk<AddDrinkContract.View>(relaxed = true)

        presenter = get()

        presenter.attachView(view)

        presenter.initialize()

        advanceUntilIdle()

        verify {
            view.setName("")
            view.setColor(defaultDrinkInfo1.themeAwareColor)
            view.setHydration(defaultDrinkInfo1.hydrationFactor)
            view.setConfirmState(false)
        }
    }


    @Test
    fun `on name change will enable confirm`() = runTest(testDispatcher) {
        val newName = "test"

        coEvery { get<GetDefaultAddDrinkInfoUseCase>().invoke() } returns defaultDrinkInfo1

        view = mockk<AddDrinkContract.View>(relaxed = true)

        presenter = get()

        presenter.attachView(view)

        presenter.initialize()

        presenter.onNameChange(newName)

        advanceUntilIdle()

        verify {
            view.setName(newName)
            view.setColor(defaultDrinkInfo1.themeAwareColor)
            view.setHydration(defaultDrinkInfo1.hydrationFactor)
            view.setConfirmState(true)
        }
    }


    @Test
    fun `cancel should dismiss bottom sheet`() = runTest(testDispatcher) {
        presenter.onCancelClick()

        advanceUntilIdle()

        verify(exactly = 1) {
            view.dismissBottomSheet()
        }
    }

    @Test
    fun `confirm should dismiss sheet`() = runTest(testDispatcher) {
        val newName = "test"

        coEvery { get<GetDefaultAddDrinkInfoUseCase>().invoke() } returns defaultDrinkInfo1

        view = mockk<AddDrinkContract.View>(relaxed = true)

        presenter = get()

        presenter.attachView(view)

        presenter.initialize()

        presenter.onNameChange(newName)

        presenter.onConfirmClick()

        advanceUntilIdle()

        coVerify(exactly = 1) {
            get<CreateWaterSourceTypeUseCase>().invoke(CreateWaterSourceTypeRequest(newName, defaultDrinkInfo1.themeAwareColor, defaultDrinkInfo1.hydrationFactor))
            view.dismissBottomSheet()
        }
    }

    @Test
    fun `default data should be set on initialization and always on attach-dettached`() =
        runTest(testDispatcher) {
            coEvery { get<GetDefaultAddDrinkInfoUseCase>().invoke() } returns defaultDrinkInfo1

            view = mockk<AddDrinkContract.View>(relaxed = true)

            presenter = get()

            presenter.attachView(view)

            presenter.initialize()

            advanceUntilIdle()

            presenter.detachView()

            advanceUntilIdle()

            presenter.attachView(view)

            advanceUntilIdle()

            verify(exactly = 2) {
                view.setName("")
                view.setColor(defaultDrinkInfo1.themeAwareColor)
                view.setHydration(defaultDrinkInfo1.hydrationFactor)
                view.setConfirmState(false)
            }
        }

    @Test
    fun `on click color should show input`() = runTest(testDispatcher) {
        coEvery { get<GetDefaultAddDrinkInfoUseCase>().invoke() } returns defaultDrinkInfo1

        view = mockk<AddDrinkContract.View>(relaxed = true)

        presenter = get()

        presenter.attachView(view)

        presenter.initialize()

        presenter.onColorClick()

        advanceUntilIdle()

        verify(exactly = 1) {
            view.showColorSelectorDialog(defaultDrinkInfo1.themeAwareColor)
        }
    }

    @Test
    fun `on click hydration should show input`() = runTest(testDispatcher) {
        coEvery { get<GetDefaultAddDrinkInfoUseCase>().invoke() } returns defaultDrinkInfo1

        view = mockk<AddDrinkContract.View>(relaxed = true)

        presenter = get()

        presenter.attachView(view)

        presenter.initialize()

        presenter.onHydrationClick()

        advanceUntilIdle()

        verify(exactly = 1) {
            view.showHydrationFactorDialog(defaultDrinkInfo1.hydrationFactor)
        }
    }
}
