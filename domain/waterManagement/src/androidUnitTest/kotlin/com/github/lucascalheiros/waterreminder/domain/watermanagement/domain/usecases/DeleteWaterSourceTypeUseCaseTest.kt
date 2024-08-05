package com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases

import com.github.lucascalheiros.waterreminder.domain.watermanagement.di.domainWaterManagementModule
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.repositories.ConsumedWaterRepository
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.repositories.DailyWaterConsumptionRepository
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.repositories.WaterSourceRepository
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.repositories.WaterSourceTypeRepository
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

class DeleteWaterSourceTypeUseCaseTest : KoinTest {

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

    private lateinit var deleteWaterSourceTypeUseCase: DeleteWaterSourceTypeUseCase

    @Before
    fun setup() {
        deleteWaterSourceTypeUseCase = get()
    }

    @Test
    fun `when delete should call repository`() = runTest(testDispatcher) {
        val id = 0L

        deleteWaterSourceTypeUseCase.invoke(id)

        coVerify(exactly = 1) {
            get<WaterSourceTypeRepository>().deleteById(id)
        }
    }

    @Test(expected = Exception::class)
    fun `when repository fails should propagate exception`() = runTest(testDispatcher) {
        val id = 0L

        coEvery { get<WaterSourceTypeRepository>().deleteById(id)} throws Exception()

        deleteWaterSourceTypeUseCase.invoke(id)
    }

}