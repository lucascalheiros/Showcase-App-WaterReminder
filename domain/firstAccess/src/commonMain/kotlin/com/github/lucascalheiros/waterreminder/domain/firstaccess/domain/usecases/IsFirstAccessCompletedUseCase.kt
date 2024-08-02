package com.github.lucascalheiros.waterreminder.domain.firstaccess.domain.usecases

interface IsFirstAccessCompletedUseCase {
    suspend operator fun invoke(): Boolean
}