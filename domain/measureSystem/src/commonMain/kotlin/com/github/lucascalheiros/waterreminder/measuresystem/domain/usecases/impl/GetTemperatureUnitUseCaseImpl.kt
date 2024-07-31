package com.github.lucascalheiros.waterreminder.measuresystem.domain.usecases.impl

import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemTemperatureUnit
import com.github.lucascalheiros.waterreminder.measuresystem.domain.repositories.TemperatureUnitRepository
import com.github.lucascalheiros.waterreminder.measuresystem.domain.usecases.GetTemperatureUnitUseCase
import kotlinx.coroutines.flow.Flow

class GetTemperatureUnitUseCaseImpl(
    private val measureSystemUnitRepository: TemperatureUnitRepository
) : GetTemperatureUnitUseCase {
    override suspend fun single(): MeasureSystemTemperatureUnit {
        return measureSystemUnitRepository.getUnit()
    }

    override fun invoke(): Flow<MeasureSystemTemperatureUnit> {
        return measureSystemUnitRepository.getUnitFlow()
    }

}
