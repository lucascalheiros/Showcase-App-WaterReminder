package com.github.lucascalheiros.waterreminder.measuresystem

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Test


class MeasureSystemWeightTest {
    @Test
    fun `same unit and value should result in equal weights`() {
        MeasureSystemUnit.entries.forEach { unit ->
            (-2..2).map { it.toDouble() }.forEach { value ->
                assertEquals(MeasureSystemWeight.create(value, unit), MeasureSystemWeight.create(value, unit))
            }
        }
    }

    @Test
    fun `different unit or value should result in different weights`() {
        MeasureSystemUnit.entries.forEach { unit1 ->
            MeasureSystemUnit.entries.forEach { unit2 ->
                (-2..2).map { it.toDouble() }.forEach { value1 ->
                    (-2..2).map { it.toDouble() }.forEach { value2 ->
                        val weight1 = MeasureSystemWeight.create(value1, unit1)
                        val weight2 = MeasureSystemWeight.create(value2, unit2)
                        when {
                            value1 == value2 && unit1 != unit2 -> assertFalse(weight1 == weight2)
                            value1 != value2 && unit1 == unit2 -> assertFalse(weight1 == weight2)
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
                    val weight1 = MeasureSystemWeight.create(value1, unit)
                    val weight2 = MeasureSystemWeight.create(value2, unit)
                    assertEquals(expected, weight1.plus(weight2, at = unit).intrinsicValue())
                }
            }
        }
    }

    @Test
    fun `conversion of values should conform equality`() {
        val zeroValue = 0.0
        val bigValue = 523409.0

        listOf(zeroValue, bigValue).forEach {
            val usWeight = MeasureSystemWeight.create(it, MeasureSystemUnit.US)
            val siWeight = MeasureSystemWeight.create(it * POUNDS_TO_GRAMS_RATE, MeasureSystemUnit.SI)
            assertNotEquals(usWeight, siWeight)
            assertEquals(siWeight, usWeight.toUnit(MeasureSystemUnit.SI))
            assertEquals(usWeight, siWeight.toUnit(MeasureSystemUnit.US))
        }

        listOf(zeroValue, bigValue).forEach {
            val ukWeight = MeasureSystemWeight.create(it, MeasureSystemUnit.UK)
            val siWeight = MeasureSystemWeight.create(it * POUNDS_TO_GRAMS_RATE, MeasureSystemUnit.SI)
            assertNotEquals(ukWeight, siWeight)
            assertEquals(siWeight, ukWeight.toUnit(MeasureSystemUnit.SI))
            assertEquals(ukWeight, siWeight.toUnit(MeasureSystemUnit.UK))
        }

        listOf(zeroValue, bigValue).forEach {
            val ukWeight = MeasureSystemWeight.create(it * POUNDS_TO_GRAMS_RATE, MeasureSystemUnit.UK)
            val siWeight = MeasureSystemWeight.create( it, MeasureSystemUnit.US)
            assertNotEquals(ukWeight, siWeight)
            assertEquals(siWeight, ukWeight.toUnit(MeasureSystemUnit.US))
            assertEquals(ukWeight, siWeight.toUnit(MeasureSystemUnit.UK))
        }
    }


    companion object {
        private const val POUNDS_TO_GRAMS_RATE = 453.59237

    }
}