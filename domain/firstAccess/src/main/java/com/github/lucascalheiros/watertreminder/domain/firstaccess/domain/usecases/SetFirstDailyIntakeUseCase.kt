package com.github.lucascalheiros.watertreminder.domain.firstaccess.domain.usecases

import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemVolume

interface SetFirstDailyIntakeUseCase {
    suspend operator fun invoke()
}