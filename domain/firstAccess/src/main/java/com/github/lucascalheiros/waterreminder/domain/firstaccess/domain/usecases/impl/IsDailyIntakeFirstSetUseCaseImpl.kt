package com.github.lucascalheiros.waterreminder.domain.firstaccess.domain.usecases.impl

import com.github.lucascalheiros.waterreminder.domain.firstaccess.domain.repositories.FirstUseFlagsRepository
import com.github.lucascalheiros.waterreminder.domain.firstaccess.domain.usecases.IsDailyIntakeFirstSetUseCase

internal class IsDailyIntakeFirstSetUseCaseImpl(
    private val firstUseFlagsRepository: FirstUseFlagsRepository
): IsDailyIntakeFirstSetUseCase {
    override suspend fun invoke(): Boolean {
        return firstUseFlagsRepository.isDailyIntakeFirstSet()
    }
}