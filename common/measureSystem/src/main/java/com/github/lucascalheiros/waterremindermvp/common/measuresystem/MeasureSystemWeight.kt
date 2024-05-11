package com.github.lucascalheiros.waterremindermvp.common.measuresystem

import com.github.lucascalheiros.waterremindermvp.common.measuresystem.impl.MeasureSystemWeightImpl

interface MeasureSystemWeight {
    fun intrinsicValue(): Double
    fun weightUnit(): MeasureSystemWeightUnit
    fun toUnit(unit: MeasureSystemWeightUnit): MeasureSystemWeight
    fun toUnit(unit: MeasureSystemUnit): MeasureSystemWeight = toUnit(unit.toWeightUnit())
    operator fun unaryMinus(): MeasureSystemWeight
    fun plus(value: MeasureSystemWeight, at: MeasureSystemWeightUnit): MeasureSystemWeight
    fun plus(value: MeasureSystemWeight, at: MeasureSystemUnit): MeasureSystemWeight = plus(value, at.toWeightUnit())
    fun minus(value: MeasureSystemWeight, at: MeasureSystemWeightUnit): MeasureSystemWeight
    fun minus(value: MeasureSystemWeight, at: MeasureSystemUnit): MeasureSystemWeight = minus(value, at.toWeightUnit())
    operator fun <T: Number> times(value: T): MeasureSystemWeight
    operator fun <T: Number> div(value: T): MeasureSystemWeight

    companion object {
        fun create(
            intrinsicValue: Double,
            measureSystemUnit: MeasureSystemWeightUnit
        ): MeasureSystemWeight {
            return MeasureSystemWeightImpl(intrinsicValue, measureSystemUnit)
        }

        fun create(
            intrinsicValue: Double,
            measureSystemUnit: MeasureSystemUnit
        ): MeasureSystemWeight {
            return MeasureSystemWeightImpl(intrinsicValue, measureSystemUnit.toWeightUnit())
        }


        fun max(a: MeasureSystemWeight, b: MeasureSystemWeight, at: MeasureSystemWeightUnit): MeasureSystemWeight {
            return create(
                a.toUnit(at).intrinsicValue().coerceAtLeast(b.toUnit(at).intrinsicValue()),
                at
            )
        }

        inline fun <T> Iterable<T>.sumOf(selector: (T) -> MeasureSystemWeight, at: MeasureSystemWeightUnit): MeasureSystemWeight {
            return map { selector(it) }.reduceOrNull { v1, v2 ->
                v1.plus(v2, at)
            } ?: create(0.0, at)
        }

    }
}