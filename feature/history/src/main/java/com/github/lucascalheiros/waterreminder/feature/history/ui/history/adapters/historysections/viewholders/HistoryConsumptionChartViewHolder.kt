package com.github.lucascalheiros.waterreminder.feature.history.ui.history.adapters.historysections.viewholders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.lucascalheiros.waterreminder.common.ui.charts.stackedbarchart.StackedBarChart
import com.github.lucascalheiros.waterreminder.feature.history.R
import com.github.lucascalheiros.waterreminder.feature.history.databinding.ListItemConsumptionChartBinding
import com.github.lucascalheiros.waterreminder.domain.history.domain.models.ChartOption
import com.github.lucascalheiros.waterreminder.domain.history.domain.models.HistoryChartPeriod
import com.github.lucascalheiros.waterreminder.feature.history.ui.history.models.HistorySections
import com.github.lucascalheiros.waterreminder.feature.history.ui.history.utils.createTooltipView
import com.github.lucascalheiros.waterreminder.feature.history.ui.history.utils.horizontalChartRuleConfig
import com.github.lucascalheiros.waterreminder.feature.history.ui.history.utils.stackBarIdentifier
import com.github.lucascalheiros.waterreminder.feature.history.ui.history.utils.toStackedColumn
import kotlinx.datetime.LocalDate
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
        val chartOption = item.historyChartData.chartPeriod.chartPeriodOption
        val stackBarColumns = item.historyChartData.consumptionVolume.map {
            it.toStackedColumn(context, chartOption)
        }
        val expectedIntakeValue = item.historyChartData.volumeIntake?.intrinsicValue()
        if (chartOption == ChartOption.Year || expectedIntakeValue == null) {
            setLineValue(null)
        } else {
            setLineValue(expectedIntakeValue)
            setMaxCustomValue(expectedIntakeValue * 1.1, animate)
        }
        setStackBarColumns(stackBarColumns, animate)
        setHorizontalRuleConfig(stackBarColumns.horizontalChartRuleConfig(chartOption))
        setTooltipStackViewProvider { column ->
            if (column.data.isEmpty()) {
                return@setTooltipStackViewProvider null
            }
            item.historyChartData.consumptionVolume.find {
                it.stackBarIdentifier == column.id
            }?.createTooltipView(context)
        }
    }

    private fun ListItemConsumptionChartBinding.setupListeners(listener: HistoryConsumptionChartListener) {
        btnWeek.setOnClickListener {
            listener.onOptionClick(ChartOption.Week)
        }
        btnMonth.setOnClickListener {
            listener.onOptionClick(ChartOption.Month)
        }
        btnYear.setOnClickListener {
            listener.onOptionClick(ChartOption.Year)
        }
        btnPrev.setOnClickListener {
            listener.onPrevClick()
        }
        btnNext.setOnClickListener {
            listener.onNextClick()
        }
    }

    private fun HistorySections.ConsumptionChart.toBtnOptionId(): Int = when (this.historyChartData.chartPeriod) {
        is HistoryChartPeriod.Month -> R.id.btnMonth
        is HistoryChartPeriod.Week -> R.id.btnWeek
        is HistoryChartPeriod.Year -> R.id.btnYear
    }

    interface HistoryConsumptionChartListener {
        fun onOptionClick(option: ChartOption)
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

private fun HistorySections.ConsumptionChart.formattedDateTitle(): String = when (val period = historyChartData.chartPeriod) {
    is HistoryChartPeriod.Month -> {
        val df = DateTimeFormatter.ofPattern("MMMM, yyyy")
        period.yearAndMonth.run { LocalDate(year, month, 1) }.toJavaLocalDate().format(df)
    }

    is HistoryChartPeriod.Week -> {
        val df = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)
        with(period) {
            "%s - %s".format(
                startDate.toJavaLocalDate().format(df),
                endDate.toJavaLocalDate().format(df)
            )
        }
    }

    is HistoryChartPeriod.Year -> {
        period.year.toString()
    }
}
