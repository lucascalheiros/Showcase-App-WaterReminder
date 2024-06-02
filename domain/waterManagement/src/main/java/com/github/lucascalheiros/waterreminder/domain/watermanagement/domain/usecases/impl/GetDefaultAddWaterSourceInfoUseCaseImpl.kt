package com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.impl

import com.github.lucascalheiros.waterreminder.common.util.requests.AsyncRequest
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.models.DefaultAddWaterSourceInfo
import com.github.lucascalheiros.waterreminder.measuresystem.domain.usecases.GetCurrentMeasureSystemUnitUseCase
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.GetDefaultAddWaterSourceInfoUseCase
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.GetWaterSourceTypeUseCase
import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemUnit
import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemVolume

internal class GetDefaultAddWaterSourceInfoUseCaseImpl(
    private val getWaterSourceTypeUseCase: GetWaterSourceTypeUseCase,
    private val getCurrentMeasureSystemUnitUseCase: GetCurrentMeasureSystemUnitUseCase
): GetDefaultAddWaterSourceInfoUseCase  {
    override suspend fun invoke(): DefaultAddWaterSourceInfo {
        val waterSourceType = getWaterSourceTypeUseCase(AsyncRequest.Single).first()
        val unit = getCurrentMeasureSystemUnitUseCase(AsyncRequest.Single)
        return DefaultAddWaterSourceInfo(
            MeasureSystemVolume.create(250.0, MeasureSystemUnit.SI).toUnit(unit),
            waterSourceType
        )
    }
}

