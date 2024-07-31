package com.github.lucascalheiros.waterreminder.feature.history.ui.history.models

import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.models.ConsumptionVolumeByType
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month

sealed interface ConsumptionVolume {
    val consumptionVolumeByType: List<ConsumptionVolumeByType>

    data class FromDay(
        val date: LocalDate,
        override val consumptionVolumeByType: List<ConsumptionVolumeByType>
    ) : ConsumptionVolume

    data class FromMonth(
        val month: Month,
        override val consumptionVolumeByType: List<ConsumptionVolumeByType>
    ) : ConsumptionVolume
}