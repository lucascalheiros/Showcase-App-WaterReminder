package com.github.lucascalheiros.waterreminder.measuresystem.domain.models

import kotlinx.serialization.Serializable
import kotlin.math.pow
import kotlin.math.round

@Serializable
internal class MeasureSystemTemperatureImpl(
    private val _intrinsicValue: Double,
    private val measureSystemUnit: MeasureSystemTemperatureUnit
) : MeasureSystemTemperature {

    private val intrinsicValue by lazy { _intrinsicValue.round() }

    override fun intrinsicValue(): Double {
        return intrinsicValue
    }

    override fun temperatureUnit(): MeasureSystemTemperatureUnit {
        return measureSystemUnit
    }

    override fun toUnit(unit: MeasureSystemTemperatureUnit): MeasureSystemTemperature {
        if (unit == measureSystemUnit) {
            return this
        }
        val standardizedSIValue = when (measureSystemUnit) {
            MeasureSystemTemperatureUnit.Celsius -> intrinsicValue
            MeasureSystemTemperatureUnit.Fahrenheit -> intrinsicValue * 5f/9f - 32
        }
        val targetIntrinsicValue = when (unit) {
            MeasureSystemTemperatureUnit.Celsius -> standardizedSIValue
            MeasureSystemTemperatureUnit.Fahrenheit -> standardizedSIValue * 9f/5f + 32
        }
        return MeasureSystemTemperatureImpl(targetIntrinsicValue, unit)
    }

    override fun unaryMinus(): MeasureSystemTemperature {
        return MeasureSystemTemperatureImpl(-intrinsicValue, measureSystemUnit)
    }

    override fun plus(value: MeasureSystemTemperature, at: MeasureSystemTemperatureUnit): MeasureSystemTemperature {
        val a = this.toUnit(at)
        val b = value.toUnit(at)
        return MeasureSystemTemperatureImpl(a.intrinsicValue() + b.intrinsicValue(), at)
    }

    override fun minus(value: MeasureSystemTemperature, at: MeasureSystemTemperatureUnit): MeasureSystemTemperature {
        val a = this.toUnit(at)
        val b = value.toUnit(at)
        return MeasureSystemTemperatureImpl(a.intrinsicValue() - b.intrinsicValue(), at)
    }

    override fun <T : Number> times(value: T): MeasureSystemTemperature {
        val newIntrinsicValue = when (value) {
            is Double -> intrinsicValue * value
            is Int -> intrinsicValue * value
            is Float -> intrinsicValue * value
            else -> throw IllegalArgumentException("Type is not supported")
        }
        return MeasureSystemTemperatureImpl(newIntrinsicValue, measureSystemUnit)
    }

    override fun <T : Number> div(value: T): MeasureSystemTemperature {
        val newIntrinsicValue = when (value) {
            is Double -> intrinsicValue / value
            is Int -> intrinsicValue / value
            is Float -> intrinsicValue / value
            else -> throw IllegalArgumentException("Type is not supported")
        }
        return MeasureSystemTemperatureImpl(newIntrinsicValue, measureSystemUnit)
    }

    override fun equals(other: Any?): Boolean {
        val otherInstance = (other as? MeasureSystemTemperature) ?: return false
        return otherInstance.intrinsicValue() == intrinsicValue() &&
                otherInstance.temperatureUnit() == temperatureUnit()
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
}