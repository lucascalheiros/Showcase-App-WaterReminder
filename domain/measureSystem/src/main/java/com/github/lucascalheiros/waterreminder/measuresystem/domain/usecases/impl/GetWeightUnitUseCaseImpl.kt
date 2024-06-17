package com.github.lucascalheiros.waterreminder.measuresystem.domain.usecases.impl

import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemWeightUnit
import com.github.lucascalheiros.waterreminder.measuresystem.domain.repositories.WeightUnitRepository
import com.github.lucascalheiros.waterreminder.measuresystem.domain.usecases.GetWeightUnitUseCase
import kotlinx.coroutines.flow.Flow

class GetWeightUnitUseCaseImpl(
    private val measureSystemUnitRepository: WeightUnitRepository
) : GetWeightUnitUseCase {
    override suspend fun single(): MeasureSystemWeightUnit {
        return measureSystemUnitRepository.getUnit()
    }

    override fun invoke(): Flow<MeasureSystemWeightUnit> {
        return measureSystemUnitRepository.getUnitFlow()
    }

}
