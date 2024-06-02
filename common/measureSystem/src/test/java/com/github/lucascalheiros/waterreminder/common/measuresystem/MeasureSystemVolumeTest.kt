package com.github.lucascalheiros.waterreminder.common.measuresystem

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Test


class MeasureSystemVolumeTest {
    @Test
    fun `same unit and value should result in equal volumes`() {
        MeasureSystemUnit.entries.forEach { unit ->
            (-2..2).map { it.toDouble() }.forEach { value ->
                assertEquals(MeasureSystemVolume.create(value, unit), MeasureSystemVolume.create(value, unit))
            }
        }
    }

    @Test
    fun `different unit or value should result in different volumes`() {
        MeasureSystemUnit.entries.forEach { unit1 ->
            MeasureSystemUnit.entries.forEach { unit2 ->
                (-2..2).map { it.toDouble() }.forEach { value1 ->
                    (-2..2).map { it.toDouble() }.forEach { value2 ->
                        val volume1 = MeasureSystemVolume.create(value1, unit1)
                        val volume2 = MeasureSystemVolume.create(value2, unit2)
                        when {
                            value1 == value2 && unit1 != unit2 -> assertFalse(volume1 == volume2)
                            value1 != value2 && unit1 == unit2 -> assertFalse(volume1 == volume2)
                        }
                    }
                }
            }
        }
    }

    @Test
    fun `sum in the same unit should be consistent as normal arithmetic operations`() {
        MeasureSystemUnit.entries.forEach { unit ->
            (-2..2).map { it.toDouble() }.forEach { value1 ->
                (-2..2).map { it.toDouble() }.forEach { value2 ->
                    val expected = value1 + value2
                    val volume1 = MeasureSystemVolume.create(value1, unit)
                    val volume2 = MeasureSystemVolume.create(value2, unit)
                    assertEquals(expected, volume1.plus(volume2, at = unit).intrinsicValue())
                }
            }
        }
    }

    @Test
    fun `conversion of values should conform equality`() {
        val zeroValue = 0.0
        val bigValue = 523409.0

        listOf(zeroValue, bigValue).forEach {
            val usVolume = MeasureSystemVolume.create(it, MeasureSystemUnit.US)
            val siVolume = MeasureSystemVolume.create(it * OZ_US_TO_ML_RATE, MeasureSystemUnit.SI)
            assertNotEquals(usVolume, siVolume)
            assertEquals(siVolume, usVolume.toUnit(MeasureSystemUnit.SI))
            assertEquals(usVolume, siVolume.toUnit(MeasureSystemUnit.US))
        }

        listOf(zeroValue, bigValue).forEach {
            val ukVolume = MeasureSystemVolume.create(it, MeasureSystemUnit.UK)
            val siVolume = MeasureSystemVolume.create(it * OZ_UK_TO_ML_RATE, MeasureSystemUnit.SI)
            assertNotEquals(ukVolume, siVolume)
            assertEquals(siVolume, ukVolume.toUnit(MeasureSystemUnit.SI))
            assertEquals(ukVolume, siVolume.toUnit(MeasureSystemUnit.UK))
        }

        listOf(zeroValue, bigValue).forEach {
            val ukVolume = MeasureSystemVolume.create(it * OZ_US_TO_UK_RATE, MeasureSystemUnit.UK)
            val siVolume = MeasureSystemVolume.create( it, MeasureSystemUnit.US)
            assertNotEquals(ukVolume, siVolume)
            assertEquals(siVolume, ukVolume.toUnit(MeasureSystemUnit.US))
            assertEquals(ukVolume, siVolume.toUnit(MeasureSystemUnit.UK))
        }
    }

    companion object {
        private const val OZ_US_TO_ML_RATE = 29.57353
        private const val OZ_UK_TO_ML_RATE = 28.4130625
        private const val OZ_US_TO_UK_RATE = OZ_US_TO_ML_RATE / OZ_UK_TO_ML_RATE
    }
}