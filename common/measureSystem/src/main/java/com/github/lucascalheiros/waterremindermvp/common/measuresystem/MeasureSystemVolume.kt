package com.github.lucascalheiros.waterremindermvp.common.measuresystem

import com.github.lucascalheiros.waterremindermvp.common.measuresystem.impl.MeasureSystemVolumeImpl


interface MeasureSystemVolume {
    fun intrinsicValue(): Double
    fun volumeUnit(): MeasureSystemVolumeUnit
    fun toUnit(unit: MeasureSystemVolumeUnit): MeasureSystemVolume
    fun toUnit(unit: MeasureSystemUnit): MeasureSystemVolume = toUnit(unit.toVolumeUnit())
    operator fun unaryMinus(): MeasureSystemVolume
    fun plus(value: MeasureSystemVolume, at: MeasureSystemVolumeUnit): MeasureSystemVolume
    fun plus(value: MeasureSystemVolume, at: MeasureSystemUnit): MeasureSystemVolume = plus(value, at.toVolumeUnit())
    fun minus(value: MeasureSystemVolume, at: MeasureSystemVolumeUnit): MeasureSystemVolume
    fun minus(value: MeasureSystemVolume, at: MeasureSystemUnit): MeasureSystemVolume = minus(value, at.toVolumeUnit())
    operator fun <T: Number> times(value: T): MeasureSystemVolume
    operator fun <T: Number> div(value: T): MeasureSystemVolume

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

        fun max(a: MeasureSystemVolume, b: MeasureSystemVolume, at: MeasureSystemVolumeUnit): MeasureSystemVolume {
            return create(
                a.toUnit(at).intrinsicValue().coerceAtLeast(b.toUnit(at).intrinsicValue()),
                at
            )
        }

        inline fun <T> Iterable<T>.sumOf(selector: (T) -> MeasureSystemVolume, at: MeasureSystemVolumeUnit): MeasureSystemVolume {
            return map { selector(it) }.reduceOrNull { v1, v2 ->
                v1.plus(v2, at)
            } ?: create(0.0, at)
        }

    }
}
