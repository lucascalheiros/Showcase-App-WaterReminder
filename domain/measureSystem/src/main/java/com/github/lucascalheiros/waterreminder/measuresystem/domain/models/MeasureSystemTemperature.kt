package com.github.lucascalheiros.waterreminder.measuresystem.domain.models

import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.impl.MeasureSystemTemperatureImpl
import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.impl.MeasureSystemVolumeImpl
import java.io.Serializable


interface MeasureSystemTemperature: Serializable {
    fun intrinsicValue(): Double

    fun temperatureUnit(): MeasureSystemTemperatureUnit

    fun toUnit(unit: MeasureSystemTemperatureUnit): MeasureSystemTemperature

    fun toUnit(unit: MeasureSystemUnit): MeasureSystemTemperature = toUnit(unit.toTemperatureUnit())

    operator fun unaryMinus(): MeasureSystemTemperature

    fun plus(value: MeasureSystemTemperature, at: MeasureSystemTemperatureUnit): MeasureSystemTemperature

    fun plus(value: MeasureSystemTemperature, at: MeasureSystemUnit): MeasureSystemTemperature =
        plus(value, at.toTemperatureUnit())

    fun minus(value: MeasureSystemTemperature, at: MeasureSystemTemperatureUnit): MeasureSystemTemperature

    fun minus(value: MeasureSystemTemperature, at: MeasureSystemUnit): MeasureSystemTemperature =
        minus(value, at.toTemperatureUnit())

    operator fun <T : Number> times(value: T): MeasureSystemTemperature

    operator fun <T : Number> div(value: T): MeasureSystemTemperature

    operator fun plus(other: MeasureSystemTemperature): MeasureSystemTemperature = plus(other, temperatureUnit())

    operator fun minus(other: MeasureSystemTemperature): MeasureSystemTemperature = minus(other, temperatureUnit())

    operator fun div(other: MeasureSystemTemperature): MeasureSystemTemperature =
        div(other.toUnit(temperatureUnit()).intrinsicValue())

    operator fun times(other: MeasureSystemTemperature): MeasureSystemTemperature =
        times(other.toUnit(temperatureUnit()).intrinsicValue())

    override fun equals(other: Any?): Boolean

    override fun hashCode(): Int

    companion object {
        fun create(
            intrinsicValue: Double,
            measureSystemUnit: MeasureSystemTemperatureUnit
        ): MeasureSystemTemperature {
            return MeasureSystemTemperatureImpl(intrinsicValue, measureSystemUnit)
        }

        fun create(
            intrinsicValue: Double,
            measureSystemUnit: MeasureSystemUnit
        ): MeasureSystemTemperature {
            return MeasureSystemTemperatureImpl(intrinsicValue, measureSystemUnit.toTemperatureUnit())
        }

        fun max(
            a: MeasureSystemTemperature,
            b: MeasureSystemTemperature,
            at: MeasureSystemTemperatureUnit
        ): MeasureSystemTemperature {
            return create(
                a.toUnit(at).intrinsicValue().coerceAtLeast(b.toUnit(at).intrinsicValue()),
                at
            )
        }

        inline fun <T> Iterable<T>.sumOfAt(
            at: MeasureSystemTemperatureUnit,
            selector: (T) -> MeasureSystemTemperature
        ): MeasureSystemTemperature {
            return map { selector(it) }.reduceOrNull { v1, v2 ->
                v1.plus(v2, at)
            } ?: create(0.0, at)
        }
    }
}
