package com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.impl

import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.models.WaterSource
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.repositories.WaterSourceRepository
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.GetWaterSourceUseCase
import com.github.lucascalheiros.waterreminder.measuresystem.domain.usecases.GetVolumeUnitUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

internal class GetWaterSourceUseCaseImpl(
    private val waterSourceRepository: WaterSourceRepository,
    private val getVolumeUnitUseCase: GetVolumeUnitUseCase
): GetWaterSourceUseCase {

    override fun invoke(): Flow<List<WaterSource>> {
        return combine(
            waterSourceRepository.allFlow(),
            getVolumeUnitUseCase()
        ) { list, unit ->
            list.map { it.copy(volume = it.volume.toUnit(unit)) }
        }
    }

}