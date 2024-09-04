package com.github.lucascalheiros.waterreminder.domain.history.domain.models

import com.github.lucascalheiros.waterreminder.common.util.date.YearAndMonth
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.models.ConsumptionVolumeByType
import kotlinx.datetime.LocalDate

sealed class ConsumptionVolume {
    abstract val consumptionVolumeByType: List<ConsumptionVolumeByType>

    data class FromDay(
        val date: LocalDate,
        override val consumptionVolumeByType: List<ConsumptionVolumeByType>
    ) : ConsumptionVolume()

    data class FromMonth(
        val yearAndMonth: YearAndMonth,
        override val consumptionVolumeByType: List<ConsumptionVolumeByType>
    ) : ConsumptionVolume()
}