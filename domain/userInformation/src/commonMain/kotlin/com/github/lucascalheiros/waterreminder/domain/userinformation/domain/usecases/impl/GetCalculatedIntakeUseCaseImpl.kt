package com.github.lucascalheiros.waterreminder.domain.userinformation.domain.usecases.impl

import com.github.lucascalheiros.waterreminder.common.util.logDebug
import com.github.lucascalheiros.waterreminder.domain.userinformation.domain.models.ActivityLevel
import com.github.lucascalheiros.waterreminder.domain.userinformation.domain.models.AmbienceTemperatureLevel
import com.github.lucascalheiros.waterreminder.domain.userinformation.domain.models.UserProfile
import com.github.lucascalheiros.waterreminder.domain.userinformation.domain.usecases.GetCalculatedIntakeUseCase
import com.github.lucascalheiros.waterreminder.domain.userinformation.domain.usecases.GetUserProfileUseCase
import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemVolume
import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemVolumeUnit
import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemWeightUnit
import com.github.lucascalheiros.waterreminder.measuresystem.domain.usecases.GetVolumeUnitUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class GetCalculatedIntakeUseCaseImpl(
    private val getUserProfileUseCase: GetUserProfileUseCase,
    private val getVolumeUnitUseCase: GetVolumeUnitUseCase
) : GetCalculatedIntakeUseCase {

    override fun invoke(): Flow<MeasureSystemVolume> {
        return combine(
            getUserProfileUseCase(),
            getVolumeUnitUseCase()
        ) { userProfile, measureSystemUnit ->
            logDebug("GetCalculatedIntakeUseCaseImpl::invoke $userProfile $measureSystemUnit")
            userProfile.calculateExpectedIntake().toUnit(measureSystemUnit)
        }
    }

    override suspend fun invoke(userProfile: UserProfile): MeasureSystemVolume {
        return userProfile.calculateExpectedIntake().toUnit(getVolumeUnitUseCase.single())
    }

    override suspend fun single(): MeasureSystemVolume {
        val profile = getUserProfileUseCase.single()
        val volumeUnit = getVolumeUnitUseCase.single()
        return profile.calculateExpectedIntake().toUnit(volumeUnit)
    }

    private fun UserProfile.calculateExpectedIntake(): MeasureSystemVolume {
        val activityBasedMultiplier =
            activityLevel.multiplier() * ACTIVITY_LEVEL_MULTIPLIER_FACTOR + MULTIPLIER_FACTOR
        val waterIntake = weight.toUnit(MeasureSystemWeightUnit.GRAMS)
            .intrinsicValue() * activityBasedMultiplier + temperatureLevel.extraIntakeInML()
        return MeasureSystemVolume.Companion.create(waterIntake, MeasureSystemVolumeUnit.ML)
    }

    private fun AmbienceTemperatureLevel.extraIntakeInML(): Double {
        return when (this) {
            AmbienceTemperatureLevel.Cold -> 0.0
            AmbienceTemperatureLevel.Moderate -> 100.0
            AmbienceTemperatureLevel.Warm -> 250.0
            AmbienceTemperatureLevel.Hot -> 500.0
        }
    }

    private fun ActivityLevel.multiplier(): Int {
        return when(this) {
            ActivityLevel.Sedentary -> 0
            ActivityLevel.Light -> 2
            ActivityLevel.Moderate -> 4
            ActivityLevel.Heavy -> 7
        }
    }

    companion object {
        private const val MULTIPLIER_FACTOR = 0.03
        private const val ACTIVITY_LEVEL_MULTIPLIER_FACTOR = 0.02 / 7
    }
}