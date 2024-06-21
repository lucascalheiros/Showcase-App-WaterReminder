package com.github.lucascalheiros.waterreminder.data.measuresystemprovider

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.github.lucascalheiros.waterreminder.data.measuresystemprovider.di.measureSystemProviderModule
import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemVolumeUnit
import com.github.lucascalheiros.waterreminder.measuresystem.domain.repositories.VolumeUnitRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.get
import org.koin.test.inject
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import kotlin.test.assertEquals

@RunWith(RobolectricTestRunner::class)
class VolumeUnitRepositoryTest : KoinTest {

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        printLogger()
        androidContext(RuntimeEnvironment.getApplication())
        modules(dataStoreModule + measureSystemProviderModule)
    }

    private val dataStoreModule = module {
        single(measureSystemDataStoreQualifier) { get<Context>().dataStore }
    }

    @Before
    fun setup() = runTest {
        get<DataStore<Preferences>>(measureSystemDataStoreQualifier).edit {
            it.clear()
        }
    }

    @Test
    fun defaultValueShouldBeMl() = runTest {
        val volumeUnitRepository: VolumeUnitRepository by inject()

        assertEquals(MeasureSystemVolumeUnit.ML, volumeUnitRepository.getUnit())
    }

    @Test
    fun defaultValueShouldBeMlForFlow() = runTest {
        val volumeUnitRepository: VolumeUnitRepository by inject()

        assertEquals(MeasureSystemVolumeUnit.ML, volumeUnitRepository.getUnitFlow().first())
    }

    @Test
    fun whenValueIsUpdatedItShouldReflect() = runTest {
        val volumeUnitRepository: VolumeUnitRepository by inject()

        volumeUnitRepository.setUnit(MeasureSystemVolumeUnit.OZ_US)

        assertEquals(MeasureSystemVolumeUnit.OZ_US, volumeUnitRepository.getUnitFlow().first())

        volumeUnitRepository.setUnit(MeasureSystemVolumeUnit.OZ_UK)

        assertEquals(MeasureSystemVolumeUnit.OZ_UK, volumeUnitRepository.getUnitFlow().first())

        volumeUnitRepository.setUnit(MeasureSystemVolumeUnit.ML)

        assertEquals(MeasureSystemVolumeUnit.ML, volumeUnitRepository.getUnitFlow().first())
    }


}
