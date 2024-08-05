package com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases

import com.github.lucascalheiros.waterreminder.domain.watermanagement.di.domainWaterManagementModule
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.models.ThemeAwareColor
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.repositories.ConsumedWaterRepository
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.repositories.DailyWaterConsumptionRepository
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.repositories.WaterSourceRepository
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.repositories.WaterSourceTypeRepository
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.requests.CreateWaterSourceTypeRequest
import com.github.lucascalheiros.waterreminder.domain.watermanagement.test.MainDispatcherRule
import com.github.lucascalheiros.waterreminder.domain.watermanagement.test.dispatchersQualifierModule
import com.github.lucascalheiros.waterreminder.domain.watermanagement.test.testDispatcher
import com.github.lucascalheiros.waterreminder.measuresystem.domain.repositories.TemperatureUnitRepository
import com.github.lucascalheiros.waterreminder.measuresystem.domain.repositories.VolumeUnitRepository
import com.github.lucascalheiros.waterreminder.measuresystem.domain.repositories.WeightUnitRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.mockkClass
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.get
import org.koin.test.mock.MockProviderRule

class CreateWaterSourceTypeUseCaseTest : KoinTest {

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        printLogger()
        modules(testModule + dispatchersQualifierModule + domainWaterManagementModule)
    }

    private val testModule = module {
        single { mockk<ConsumedWaterRepository>(relaxed = true) }
        single { mockk<DailyWaterConsumptionRepository>(relaxed = true) }
        single { mockk<WaterSourceRepository>(relaxed = true) }
        single { mockk<WaterSourceTypeRepository>(relaxed = true) }
        single { mockk<TemperatureUnitRepository>(relaxed = true) }
        single { mockk<VolumeUnitRepository>(relaxed = true) }
        single { mockk<WeightUnitRepository>(relaxed = true) }
    }

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @get:Rule
    val mockProvider = MockProviderRule.create { clazz ->
        mockkClass(clazz)
    }

    private lateinit var createWaterSourceTypeUseCase: CreateWaterSourceTypeUseCase

    @Before
    fun setup() {
        createWaterSourceTypeUseCase = get()
    }

    @Test
    fun `when create water source should call repository`() = runTest(testDispatcher) {
        val createRequest = CreateWaterSourceTypeRequest(
            "test",
            ThemeAwareColor(0, 1),
            1f
        )

        createWaterSourceTypeUseCase.invoke(createRequest)

        coVerify(exactly = 1) {
            get<WaterSourceTypeRepository>().create(createRequest)
        }
    }

    @Test(expected = Exception::class)
    fun `when repository fails should propagate exception`() = runTest(testDispatcher) {
        val createRequest = CreateWaterSourceTypeRequest(
            "test",
            ThemeAwareColor(0, 1),
            1f
        )

        coEvery { get<WaterSourceTypeRepository>().create(createRequest) } throws Exception()

        createWaterSourceTypeUseCase.invoke(createRequest)
    }

}
