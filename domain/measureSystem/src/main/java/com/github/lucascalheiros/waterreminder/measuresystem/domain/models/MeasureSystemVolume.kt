package com.github.lucascalheiros.waterreminder.measuresystem.domain.models

import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.impl.MeasureSystemVolumeImpl
import java.io.Serializable


interface MeasureSystemVolume: Serializable {
    fun intrinsicValue(): Double

    fun volumeUnit(): MeasureSystemVolumeUnit

    fun toUnit(unit: MeasureSystemVolumeUnit): MeasureSystemVolume

    fun toUnit(unit: MeasureSystemUnit): MeasureSystemVolume = toUnit(unit.toVolumeUnit())

    operator fun unaryMinus(): MeasureSystemVolume

    fun plus(value: MeasureSystemVolume, at: MeasureSystemVolumeUnit): MeasureSystemVolume

    fun plus(value: MeasureSystemVolume, at: MeasureSystemUnit): MeasureSystemVolume =
        plus(value, at.toVolumeUnit())

    fun minus(value: MeasureSystemVolume, at: MeasureSystemVolumeUnit): MeasureSystemVolume

    fun minus(value: MeasureSystemVolume, at: MeasureSystemUnit): MeasureSystemVolume =
        minus(value, at.toVolumeUnit())

    operator fun <T : Number> times(value: T): MeasureSystemVolume

    operator fun <T : Number> div(value: T): MeasureSystemVolume

    operator fun plus(other: MeasureSystemVolume): MeasureSystemVolume = plus(other, volumeUnit())

    operator fun minus(other: MeasureSystemVolume): MeasureSystemVolume = minus(other, volumeUnit())

    operator fun div(other: MeasureSystemVolume): MeasureSystemVolume =
        div(other.toUnit(volumeUnit()).intrinsicValue())

    operator fun times(other: MeasureSystemVolume): MeasureSystemVolume =
        times(other.toUnit(volumeUnit()).intrinsicValue())

    override fun equals(other: Any?): Boolean

    override fun hashCode(): Int

    companion object {
        fun create(
            intrinsicValue: Double,
            measureSystemUnit: MeasureSystemVolumeUnit
        ): MeasureSystemVolume {
            return MeasureSystemVolumeImpl(intrinsicValue, measureSystemUnit)
        }

        fun create(
            intrinsicValue: Double,
            measureSystemUnit: MeasureSystemUnit
        ): MeasureSystemVolume {
            return MeasureSystemVolumeImpl(intrinsicValue, measureSystemUnit.toVolumeUnit())
        }

        fun max(
            a: MeasureSystemVolume,
            b: MeasureSystemVolume,
            at: MeasureSystemVolumeUnit
        ): MeasureSystemVolume {
            return create(
                a.toUnit(at).intrinsicValue().coerceAtLeast(b.toUnit(at).intrinsicValue()),
                at
            )
        }

        inline fun <T> Iterable<T>.sumOfAt(
            at: MeasureSystemVolumeUnit,
            selector: (T) -> MeasureSystemVolume
        ): MeasureSystemVolume {
            return map { selector(it) }.reduceOrNull { v1, v2 ->
                v1.plus(v2, at)
            } ?: create(0.0, at)
        }
    }
}
