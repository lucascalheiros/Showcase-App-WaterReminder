package com.github.lucascalheiros.waterremindermvp.domain.watermanagement.domain.usecases.impl

import com.github.lucascalheiros.waterremindermvp.common.measuresystem.MeasureSystemUnit
import com.github.lucascalheiros.waterremindermvp.common.measuresystem.MeasureSystemVolume
import com.github.lucascalheiros.waterremindermvp.domain.watermanagement.domain.models.DefaultAddWaterSourceInfo
import com.github.lucascalheiros.waterremindermvp.domain.watermanagement.domain.usecases.GetCurrentMeasureSystemUnitUseCase
import com.github.lucascalheiros.waterremindermvp.domain.watermanagement.domain.usecases.GetDefaultAddWaterSourceInfoUseCase
import com.github.lucascalheiros.waterremindermvp.domain.watermanagement.domain.usecases.GetWaterSourceTypeUseCase
import com.github.lucascalheiros.waterremindermvp.domain.watermanagement.domain.usecases.requests.AsyncRequest

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

