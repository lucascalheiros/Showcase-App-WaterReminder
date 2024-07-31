package com.github.lucascalheiros.waterreminder.measuresystem.domain.models.impl

import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemVolume
import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemVolumeUnit
import kotlin.math.pow
import kotlin.math.round

internal class MeasureSystemVolumeImpl(
    intrinsicValue: Double,
    private val measureSystemUnit: MeasureSystemVolumeUnit
) : MeasureSystemVolume {

    private val intrinsicValue = intrinsicValue.round()

    override fun intrinsicValue(): Double {
        return intrinsicValue
    }

    override fun volumeUnit(): MeasureSystemVolumeUnit {
        return measureSystemUnit
    }

    override fun toUnit(unit: MeasureSystemVolumeUnit): MeasureSystemVolume {
        if (unit == measureSystemUnit) {
            return this
        }
        val standardizedSIValue = when (measureSystemUnit) {
            MeasureSystemVolumeUnit.ML -> intrinsicValue
            MeasureSystemVolumeUnit.OZ_UK -> intrinsicValue * OZ_UK_TO_ML_RATE
            MeasureSystemVolumeUnit.OZ_US -> intrinsicValue * OZ_US_TO_ML_RATE
        }
        val targetIntrinsicValue = when (unit) {
            MeasureSystemVolumeUnit.ML -> standardizedSIValue
            MeasureSystemVolumeUnit.OZ_UK -> standardizedSIValue / OZ_UK_TO_ML_RATE
            MeasureSystemVolumeUnit.OZ_US -> standardizedSIValue / OZ_US_TO_ML_RATE
        }
        return MeasureSystemVolumeImpl(targetIntrinsicValue, unit)
    }

    override fun unaryMinus(): MeasureSystemVolume {
        return MeasureSystemVolumeImpl(-intrinsicValue, measureSystemUnit)
    }

    override fun plus(value: MeasureSystemVolume, at: MeasureSystemVolumeUnit): MeasureSystemVolume {
        val a = this.toUnit(at)
        val b = value.toUnit(at)
        return MeasureSystemVolumeImpl(a.intrinsicValue() + b.intrinsicValue(), at)
    }

    override fun minus(value: MeasureSystemVolume, at: MeasureSystemVolumeUnit): MeasureSystemVolume {
        val a = this.toUnit(at)
        val b = value.toUnit(at)
        return MeasureSystemVolumeImpl(a.intrinsicValue() - b.intrinsicValue(), at)
    }

    override fun <T : Number> times(value: T): MeasureSystemVolume {
        val newIntrinsicValue = when (value) {
            is Double -> intrinsicValue * value
            is Int -> intrinsicValue * value
            is Float -> intrinsicValue * value
            else -> throw IllegalArgumentException("Type is not supported")
        }
        return MeasureSystemVolumeImpl(newIntrinsicValue, measureSystemUnit)
    }

    override fun <T : Number> div(value: T): MeasureSystemVolume {
        val newIntrinsicValue = when (value) {
            is Double -> intrinsicValue / value
            is Int -> intrinsicValue / value
            is Float -> intrinsicValue / value
            else -> throw IllegalArgumentException("Type is not supported")
        }
        return MeasureSystemVolumeImpl(newIntrinsicValue, measureSystemUnit)
    }

    override fun equals(other: Any?): Boolean {
        val otherInstance = (other as? MeasureSystemVolume) ?: return false
        return otherInstance.intrinsicValue() == intrinsicValue() &&
                otherInstance.volumeUnit() == volumeUnit()
    }

    override fun hashCode(): Int {
        var result = intrinsicValue.hashCode()
        result = 31 * result + measureSystemUnit.hashCode()
        return result
    }

    private fun Double.round(precision: Int = 6): Double {
        val precisionRight = 10.0.pow(precision)
        return round(this * precisionRight) / precisionRight
    }

    companion object {
        private const val OZ_US_TO_ML_RATE = 29.57353
        private const val OZ_UK_TO_ML_RATE = 28.4130625
    }
}