package com.github.lucascalheiros.waterreminder.common.util.date

data class YearAndMonth(val year: Int, val month: Int): Comparable<YearAndMonth> {
    override fun compareTo(other: YearAndMonth): Int {
        return (year - other.year).let {
            if (it == 0) {
                month - other.month
            } else {
                it
            }
        }
    }
}