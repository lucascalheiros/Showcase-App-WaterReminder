package com.github.lucascalheiros.waterreminder.domain.userinformation.domain.usecases.impl

import com.github.lucascalheiros.waterreminder.domain.userinformation.domain.models.AmbienceTemperatureLevel
import com.github.lucascalheiros.waterreminder.domain.userinformation.domain.models.UserProfile
import com.github.lucascalheiros.waterreminder.domain.userinformation.domain.usecases.GetCalculatedIntakeUseCase
import com.github.lucascalheiros.waterreminder.domain.userinformation.domain.usecases.GetUserProfileUseCase
import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemVolume
import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemVolumeUnit
import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemWeightUnit
import com.github.lucascalheiros.waterreminder.measuresystem.domain.usecases.GetCurrentMeasureSystemUnitUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class GetCalculatedIntakeUseCaseImpl(
    private val getUserProfileUseCase: GetUserProfileUseCase,
    private val getCurrentMeasureSystemUnitUseCase: GetCurrentMeasureSystemUnitUseCase
) : GetCalculatedIntakeUseCase {

    override fun invoke(): Flow<MeasureSystemVolume> {
        return combine(
            getUserProfileUseCase(),
            getCurrentMeasureSystemUnitUseCase()
        ) { userProfile, measureSystemUnit ->
            userProfile.calculateExpectedIntake().toUnit(measureSystemUnit)
        }
    }

    override suspend fun invoke(userProfile: UserProfile): MeasureSystemVolume {
        return userProfile.calculateExpectedIntake().toUnit(getCurrentMeasureSystemUnitUseCase.single())
    }

    private fun UserProfile.calculateExpectedIntake(): MeasureSystemVolume {
        val activityBasedMultiplier =
            activityLevelInWeekDays * ACTIVITY_LEVEL_MULTIPLIER_FACTOR + MULTIPLIER_FACTOR
        val waterIntake = weight.toUnit(MeasureSystemWeightUnit.GRAMS)
            .intrinsicValue() * activityBasedMultiplier + temperatureLevel.extraIntakeInML()
        return MeasureSystemVolume.Companion.create(waterIntake, MeasureSystemVolumeUnit.ML)
    }

    private fun AmbienceTemperatureLevel.extraIntakeInML(): Double {
        return when (this) {
            AmbienceTemperatureLevel.Cold -> 0.0
            AmbienceTemperatureLevel.Moderate -> 100.0
            AmbienceTemperatureLevel.Warn -> 250.0
            AmbienceTemperatureLevel.Hot -> 500.0
        }
    }

    companion object {
        private const val MULTIPLIER_FACTOR = 0.03
        private const val ACTIVITY_LEVEL_MULTIPLIER_FACTOR = 0.02 / 7
    }
}