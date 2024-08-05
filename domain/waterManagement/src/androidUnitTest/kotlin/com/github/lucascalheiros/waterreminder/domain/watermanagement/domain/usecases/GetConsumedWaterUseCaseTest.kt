package com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases

import com.github.lucascalheiros.waterreminder.common.util.date.TimeInterval
import com.github.lucascalheiros.waterreminder.domain.watermanagement.di.domainWaterManagementModule
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.repositories.ConsumedWaterRepository
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.repositories.DailyWaterConsumptionRepository
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.repositories.WaterSourceRepository
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.repositories.WaterSourceTypeRepository
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.requests.ConsumedWaterRequest
import com.github.lucascalheiros.waterreminder.domain.watermanagement.test.MainDispatcherRule
import com.github.lucascalheiros.waterreminder.domain.watermanagement.test.consumedWater1InDay1
import com.github.lucascalheiros.waterreminder.domain.watermanagement.test.consumedWater2InDay2
import com.github.lucascalheiros.waterreminder.domain.watermanagement.test.consumedWater3InDay3
import com.github.lucascalheiros.waterreminder.domain.watermanagement.test.consumedWater4InDay4
import com.github.lucascalheiros.waterreminder.domain.watermanagement.test.consumedWater5InDay5
import com.github.lucascalheiros.waterreminder.domain.watermanagement.test.consumedWater6InDay4dot5
import com.github.lucascalheiros.waterreminder.domain.watermanagement.test.dispatchersQualifierModule
import com.github.lucascalheiros.waterreminder.domain.watermanagement.test.testDispatcher
import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemVolumeUnit
import com.github.lucascalheiros.waterreminder.measuresystem.domain.repositories.TemperatureUnitRepository
import com.github.lucascalheiros.waterreminder.measuresystem.domain.repositories.VolumeUnitRepository
import com.github.lucascalheiros.waterreminder.measuresystem.domain.repositories.WeightUnitRepository
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.mockkClass
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.get
import org.koin.test.mock.MockProviderRule
import kotlin.test.assertContentEquals
import kotlin.time.DurationUnit
import kotlin.time.toDuration

class GetConsumedWaterUseCaseTest : KoinTest {

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

    private lateinit var getConsumedWaterUseCase: GetConsumedWaterUseCase

    @Before
    fun setup() {
        getConsumedWaterUseCase = get()
    }

    private val consumedWaterList = listOf(
        consumedWater1InDay1,
        consumedWater2InDay2,
        consumedWater3InDay3,
        consumedWater4InDay4,
        consumedWater5InDay5
    )

    @Test
    fun `when requesting period should return based on period`() = runTest(testDispatcher) {
        val volumeFlow = MutableStateFlow(MeasureSystemVolumeUnit.ML)
        val consumedWaterFlow = MutableStateFlow(consumedWaterList)

        val fromDay2to4 = ConsumedWaterRequest.FromTimeInterval(
            TimeInterval(
                2.toDuration(DurationUnit.DAYS).inWholeMilliseconds,
                4.toDuration(DurationUnit.DAYS).inWholeMilliseconds
            )
        )

        coEvery { get<VolumeUnitRepository>().getUnitFlow() } returns volumeFlow
        coEvery { get<ConsumedWaterRepository>().allFlow() } returns consumedWaterFlow

        assertContentEquals(
            listOf(consumedWater2InDay2, consumedWater3InDay3, consumedWater4InDay4),
            getConsumedWaterUseCase.invoke(fromDay2to4).first()
        )

        consumedWaterFlow.update { consumedWaters ->
            consumedWaters.filter { it.consumedWaterId != consumedWater3InDay3.consumedWaterId }
        }

        assertContentEquals(
            listOf(consumedWater2InDay2, consumedWater4InDay4),
            getConsumedWaterUseCase.invoke(fromDay2to4).first()
        )
    }

    @Test
    fun `when requesting from last days should return based on period`() = runTest(testDispatcher) {
        val volumeFlow = MutableStateFlow(MeasureSystemVolumeUnit.ML)
        val consumedWaterFlow = MutableStateFlow(consumedWaterList)

        val fromLast3Days = ConsumedWaterRequest.ItemsFromLastDays(3)

        coEvery { get<VolumeUnitRepository>().getUnitFlow() } returns volumeFlow
        coEvery { get<ConsumedWaterRepository>().allFlow() } returns consumedWaterFlow

        assertContentEquals(
            listOf(consumedWater3InDay3, consumedWater4InDay4, consumedWater5InDay5),
            getConsumedWaterUseCase.invoke(fromLast3Days).first()
        )

        consumedWaterFlow.update { consumedWaters ->
            consumedWaters.filter { it.consumedWaterId != consumedWater3InDay3.consumedWaterId } + listOf(consumedWater6InDay4dot5)
        }

        assertContentEquals(
            listOf(consumedWater2InDay2, consumedWater4InDay4, consumedWater5InDay5, consumedWater6InDay4dot5),
            getConsumedWaterUseCase.invoke(fromLast3Days).first()
        )
    }

}