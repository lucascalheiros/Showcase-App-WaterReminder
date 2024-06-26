package com.github.lucascalheiros.waterreminder.domain.firstaccess.domain.usecases.impl

import com.github.lucascalheiros.waterreminder.domain.firstaccess.domain.repositories.FirstUseFlagsRepository
import com.github.lucascalheiros.waterreminder.domain.firstaccess.domain.usecases.IsFirstAccessCompletedUseCase

internal class IsFirstAccessCompletedUseCaseImpl(
    private val firstUseFlagsRepository: FirstUseFlagsRepository
): IsFirstAccessCompletedUseCase {
    override suspend fun invoke(): Boolean {
        return firstUseFlagsRepository.isFirstAccessCompleted()
    }
}