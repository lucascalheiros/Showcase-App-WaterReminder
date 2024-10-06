package com.github.lucascalheiros.waterreminder.domain.firstaccess.domain.usecases.impl

import com.github.lucascalheiros.waterreminder.domain.firstaccess.domain.repositories.FirstUseFlagsRepository
import com.github.lucascalheiros.waterreminder.domain.firstaccess.domain.usecases.IsFirstAccessCompletedUseCase
import kotlinx.coroutines.flow.Flow

internal class IsFirstAccessCompletedUseCaseImpl(
    private val firstUseFlagsRepository: FirstUseFlagsRepository
): IsFirstAccessCompletedUseCase {
    override fun invoke(): Flow<Boolean> {
        return firstUseFlagsRepository.isFirstAccessCompletedFlow()
    }

    override suspend fun single(): Boolean {
        return firstUseFlagsRepository.isFirstAccessCompleted()
    }
}