package com.github.lucascalheiros.waterreminder.common.measuresystem.impl

import com.github.lucascalheiros.waterreminder.common.measuresystem.MeasureSystemWeight
import com.github.lucascalheiros.waterreminder.common.measuresystem.MeasureSystemWeightUnit

internal class MeasureSystemWeightImpl(
    private val intrinsicValue: Double,
    private val measureSystemUnit: MeasureSystemWeightUnit
) : MeasureSystemWeight {

    override fun intrinsicValue(): Double {
        return intrinsicValue
    }

    override fun weightUnit(): MeasureSystemWeightUnit {
        return measureSystemUnit
    }

    override fun toUnit(unit: MeasureSystemWeightUnit): MeasureSystemWeight {
        if (unit == measureSystemUnit) {
            return this
        }
        val standardizedSIValue = when (measureSystemUnit) {
            MeasureSystemWeightUnit.GRAMS -> intrinsicValue
            MeasureSystemWeightUnit.POUNDS -> intrinsicValue * POUNDS_TO_GRAMS_RATE
        }
        val targetIntrinsicValue = when (unit) {
            MeasureSystemWeightUnit.GRAMS -> standardizedSIValue
            MeasureSystemWeightUnit.POUNDS -> standardizedSIValue / POUNDS_TO_GRAMS_RATE
        }
        return MeasureSystemWeightImpl(targetIntrinsicValue, unit)
    }

    override fun unaryMinus(): MeasureSystemWeight {
        return MeasureSystemWeightImpl(-intrinsicValue, measureSystemUnit)
    }

    override fun plus(value: MeasureSystemWeight, at: MeasureSystemWeightUnit): MeasureSystemWeight {
        val a = this.toUnit(at)
        val b = value.toUnit(at)
        return MeasureSystemWeightImpl(a.intrinsicValue() + b.intrinsicValue(), at)
    }


    override fun minus(value: MeasureSystemWeight, at: MeasureSystemWeightUnit): MeasureSystemWeight {
        val a = this.toUnit(at)
        val b = value.toUnit(at)
        return MeasureSystemWeightImpl(a.intrinsicValue() - b.intrinsicValue(), at)
    }

    override fun <T : Number> times(value: T): MeasureSystemWeight {
        val newIntrinsicValue = when (value) {
            is Double -> intrinsicValue * value
            is Int -> intrinsicValue * value
            is Float -> intrinsicValue * value
            else -> throw IllegalArgumentException("Type is not supported")
        }
        return MeasureSystemWeightImpl(newIntrinsicValue, measureSystemUnit)
    }

    override fun <T : Number> div(value: T): MeasureSystemWeight {
        val newIntrinsicValue = when (value) {
            is Double -> intrinsicValue / value
            is Int -> intrinsicValue / value
            is Float -> intrinsicValue / value
            else -> throw IllegalArgumentException("Type is not supported")
        }
        return MeasureSystemWeightImpl(newIntrinsicValue, measureSystemUnit)
    }

    override fun equals(other: Any?): Boolean {
        val otherInstance = (other as? MeasureSystemWeight) ?: return false
        return otherInstance.intrinsicValue() == intrinsicValue() &&
                otherInstance.weightUnit() == weightUnit()
    }
    override fun hashCode(): Int {
        var result = intrinsicValue.hashCode()
        result = 31 * result + measureSystemUnit.hashCode()
        return result
    }

    companion object {
        private const val POUNDS_TO_GRAMS_RATE = 453.59237
    }
}