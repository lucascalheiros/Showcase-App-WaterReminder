package com.github.lucascalheiros.waterreminder.domain.firstaccess.domain.usecases.impl

import com.github.lucascalheiros.waterreminder.domain.userinformation.domain.usecases.GetCalculatedIntakeUseCase
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.SaveDailyWaterConsumptionUseCase
import com.github.lucascalheiros.waterreminder.domain.firstaccess.domain.repositories.FirstUseFlagsRepository
import com.github.lucascalheiros.waterreminder.domain.firstaccess.domain.usecases.IsDailyIntakeFirstSetUseCase
import com.github.lucascalheiros.waterreminder.domain.firstaccess.domain.usecases.SetFirstDailyIntakeUseCase

internal class SetFirstDailyIntakeUseCaseImpl(
    private val firstUseFlagsRepository: FirstUseFlagsRepository,
    private val isDailyIntakeFirstSetUseCase: IsDailyIntakeFirstSetUseCase,
    private val getCalculatedIntakeUseCase: GetCalculatedIntakeUseCase,
    private val saveDailyWaterConsumptionUseCase: SaveDailyWaterConsumptionUseCase,
): SetFirstDailyIntakeUseCase {
    override suspend fun invoke() {
        val isFirst = isDailyIntakeFirstSetUseCase()
        if (isFirst) {
            val calculatedIntake = getCalculatedIntakeUseCase.single()
            saveDailyWaterConsumptionUseCase(calculatedIntake)
            firstUseFlagsRepository.setFirstAccessCompletedFlag(true)
        }
    }

}