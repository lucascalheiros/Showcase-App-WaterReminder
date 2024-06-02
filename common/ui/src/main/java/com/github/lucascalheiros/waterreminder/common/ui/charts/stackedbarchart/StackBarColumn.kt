package com.github.lucascalheiros.waterreminder.common.ui.charts.stackedbarchart

data class StackBarColumn(
    val id: String,
    val data: List<StackData>,
    val label: String = "",
) {
    val columnValue: Double by lazy { data.sumOf { it.value } }
}