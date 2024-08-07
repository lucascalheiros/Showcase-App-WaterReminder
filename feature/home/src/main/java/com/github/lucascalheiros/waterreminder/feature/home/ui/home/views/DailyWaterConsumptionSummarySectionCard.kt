package com.github.lucascalheiros.waterreminder.feature.home.ui.home.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.github.lucascalheiros.waterreminder.common.appcore.format.shortUnitFormatted
import com.github.lucascalheiros.waterreminder.common.appcore.format.shortValueFormatted
import com.github.lucascalheiros.waterreminder.common.ui.ColorChartData
import com.github.lucascalheiros.waterreminder.common.ui.getThemeAwareColor
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.models.DailyWaterConsumptionSummary
import com.github.lucascalheiros.waterreminder.feature.home.databinding.ViewDailyWaterConsumptionSummaryCardBinding
import kotlin.math.roundToInt

class DailyWaterConsumptionSummarySectionCard @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding = ViewDailyWaterConsumptionSummaryCardBinding.inflate(
        LayoutInflater.from(context),
        this
    )

    fun setDailyWaterConsumptionSummary(
        summary: DailyWaterConsumptionSummary,
        animateChart: Boolean
    ) {
        binding.tvPercentage.text = summary.percentage.roundToInt().toString()
        binding.tvIntakeValue.text = summary.intake.shortValueFormatted(context)
        binding.tvIntakeUnit.text = summary.intake.shortUnitFormatted(context)
        val colorChartDataList = summary.consumptionPercentageByType.map {
            ColorChartData(
                it.waterSourceType.waterSourceTypeId,
                it.waterSourceType.run {
                    context.getThemeAwareColor(
                        lightColor,
                        darkColor
                    ).toInt()
                }, it.percentage
            )
        }
        if (animateChart) {
            binding.cccChart.setColorAndPercentages(colorChartDataList, 1000L)
        } else {
            binding.cccChart.setColorAndPercentages(colorChartDataList)
        }
    }

}