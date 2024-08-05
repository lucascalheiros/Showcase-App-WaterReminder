package com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.impl

import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.models.DefaultVolumeShortcuts
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.GetDefaultVolumeShortcutsUseCase
import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemVolume
import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemVolumeUnit
import com.github.lucascalheiros.waterreminder.measuresystem.domain.usecases.GetVolumeUnitUseCase

internal class GetDefaultVolumeShortcutsUseCaseImpl(
    private val getVolumeUnitUseCase: GetVolumeUnitUseCase
): GetDefaultVolumeShortcutsUseCase {

    override suspend fun invoke(): DefaultVolumeShortcuts {
        return with(getVolumeUnitUseCase.single()) {
            DefaultVolumeShortcuts(
                smallestVolume(),
                smallVolume(),
                mediumVolume(),
                largeVolume()
            )
        }
    }

    private fun MeasureSystemVolumeUnit.smallestVolume(): MeasureSystemVolume {
        val intrinsicValue = when (this) {
            MeasureSystemVolumeUnit.ML -> 100.0
            MeasureSystemVolumeUnit.OZ_UK,
            MeasureSystemVolumeUnit.OZ_US -> 4.0
        }
        return MeasureSystemVolume.create(intrinsicValue, this)
    }

    private fun MeasureSystemVolumeUnit.smallVolume(): MeasureSystemVolume {
        val intrinsicValue = when (this) {
            MeasureSystemVolumeUnit.ML -> 180.0
            MeasureSystemVolumeUnit.OZ_UK,
            MeasureSystemVolumeUnit.OZ_US -> 6.0
        }
        return MeasureSystemVolume.create(intrinsicValue, this)
    }

    private fun MeasureSystemVolumeUnit.mediumVolume(): MeasureSystemVolume {
        val intrinsicValue = when (this) {
            MeasureSystemVolumeUnit.ML -> 250.0
            MeasureSystemVolumeUnit.OZ_UK,
            MeasureSystemVolumeUnit.OZ_US -> 8.0
        }
        return MeasureSystemVolume.create(intrinsicValue, this)
    }

    private fun MeasureSystemVolumeUnit.largeVolume(): MeasureSystemVolume {
        val intrinsicValue = when (this) {
            MeasureSystemVolumeUnit.ML -> 500.0
            MeasureSystemVolumeUnit.OZ_UK,
            MeasureSystemVolumeUnit.OZ_US -> 16.0
        }
        return MeasureSystemVolume.create(intrinsicValue, this)
    }

}