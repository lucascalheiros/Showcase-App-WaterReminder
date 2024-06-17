package com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.impl

import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.models.DefaultAddWaterSourceInfo
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.GetDefaultAddWaterSourceInfoUseCase
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.GetWaterSourceTypeUseCase
import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemVolume
import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemVolumeUnit
import com.github.lucascalheiros.waterreminder.measuresystem.domain.usecases.GetVolumeUnitUseCase

internal class GetDefaultAddWaterSourceInfoUseCaseImpl(
    private val getWaterSourceTypeUseCase: GetWaterSourceTypeUseCase,
    private val getVolumeUnitUseCase: GetVolumeUnitUseCase
): GetDefaultAddWaterSourceInfoUseCase  {
    override suspend fun invoke(): DefaultAddWaterSourceInfo {
        val waterSourceType = getWaterSourceTypeUseCase.single().first()
        val unit = getVolumeUnitUseCase.single()
        return DefaultAddWaterSourceInfo(
            MeasureSystemVolume.create(250.0, MeasureSystemVolumeUnit.ML).toUnit(unit),
            waterSourceType
        )
    }
}

