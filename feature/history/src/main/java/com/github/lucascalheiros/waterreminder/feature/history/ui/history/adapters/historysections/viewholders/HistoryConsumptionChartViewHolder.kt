package com.github.lucascalheiros.waterreminder.feature.history.ui.history.adapters.historysections.viewholders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.lucascalheiros.waterreminder.common.ui.charts.stackedbarchart.StackedBarChart
import com.github.lucascalheiros.waterreminder.feature.history.R
import com.github.lucascalheiros.waterreminder.feature.history.databinding.ListItemConsumptionChartBinding
import com.github.lucascalheiros.waterreminder.feature.history.ui.history.ChartOptions
import com.github.lucascalheiros.waterreminder.feature.history.ui.history.models.HistorySections
import com.github.lucascalheiros.waterreminder.feature.history.ui.history.utils.createTooltipView
import com.github.lucascalheiros.waterreminder.feature.history.ui.history.utils.horizontalChartRuleConfig
import com.github.lucascalheiros.waterreminder.feature.history.ui.history.utils.stackBarIdentifier
import com.github.lucascalheiros.waterreminder.feature.history.ui.history.utils.toStackedColumn
import kotlinx.datetime.toJavaLocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

class HistoryConsumptionChartViewHolder(private val binding: ListItemConsumptionChartBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(
        item: HistorySections.ConsumptionChart,
        listener: HistoryConsumptionChartListener,
        animate: Boolean
    ): Unit = with(binding) {
        setupListeners(listener)
        stackedBarChart.setupStackedBarData(item, animate)
        tbgChartOptions.check(item.toBtnOptionId())
        tvChartDateTitle.text = item.formattedDateTitle()
    }

    private fun StackedBarChart.setupStackedBarData(
        item: HistorySections.ConsumptionChart,
        animate: Boolean
    ) {
        val stackBarColumns = item.consumptionVolume.map {
            it.toStackedColumn(context, item.chartPeriodOption)
        }
        val expectedIntakeValue = item.volumeIntake.intrinsicValue()
        if (item is HistorySections.ConsumptionChart.Year) {
            setLineValue(null)
            setMaxCustomValue(expectedIntakeValue, false)
        } else {
            setLineValue(expectedIntakeValue)
            setMaxCustomValue(expectedIntakeValue * 1.1, animate)
        }
        setStackBarColumns(stackBarColumns, animate)
        setHorizontalRuleConfig(stackBarColumns.horizontalChartRuleConfig(item.chartPeriodOption))
        setTooltipStackViewProvider { column ->
            if (column.data.isEmpty()) {
                return@setTooltipStackViewProvider null
            }
            item.consumptionVolume.find {
                it.stackBarIdentifier == column.id
            }?.createTooltipView(context)
        }
    }

    private fun ListItemConsumptionChartBinding.setupListeners(listener: HistoryConsumptionChartListener) {
        btnWeek.setOnClickListener {
            listener.onOptionClick(ChartOptions.Week)
        }
        btnMonth.setOnClickListener {
            listener.onOptionClick(ChartOptions.Month)
        }
        btnYear.setOnClickListener {
            listener.onOptionClick(ChartOptions.Year)
        }
        btnPrev.setOnClickListener {
            listener.onPrevClick()
        }
        btnNext.setOnClickListener {
            listener.onNextClick()
        }
    }

    private fun HistorySections.ConsumptionChart.toBtnOptionId(): Int = when (this) {
        is HistorySections.ConsumptionChart.Month -> R.id.btnMonth
        is HistorySections.ConsumptionChart.Week -> R.id.btnWeek
        is HistorySections.ConsumptionChart.Year -> R.id.btnYear
    }

    interface HistoryConsumptionChartListener {
        fun onOptionClick(option: ChartOptions)
        fun onPrevClick()
        fun onNextClick()
    }

    companion object {
        fun inflate(parent: ViewGroup): HistoryConsumptionChartViewHolder =
            HistoryConsumptionChartViewHolder(
                ListItemConsumptionChartBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
    }
}

private fun HistorySections.ConsumptionChart.formattedDateTitle(): String = when (this) {
    is HistorySections.ConsumptionChart.Month -> {
        val df = DateTimeFormatter.ofPattern("MMMM, yyyy")
        yearMonth.format(df)
    }

    is HistorySections.ConsumptionChart.Week -> {
        val df = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)
        dateRange.let {
            "%s - %s".format(
                it.first.toJavaLocalDate().format(df),
                it.second.toJavaLocalDate().format(df)
            )
        }
    }

    is HistorySections.ConsumptionChart.Year -> {
        year.toString()
    }
}
