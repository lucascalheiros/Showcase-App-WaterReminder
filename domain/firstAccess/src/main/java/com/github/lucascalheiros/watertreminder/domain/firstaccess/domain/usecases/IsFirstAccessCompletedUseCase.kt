package com.github.lucascalheiros.watertreminder.domain.firstaccess.domain.usecases

interface IsFirstAccessCompletedUseCase {
    suspend operator fun invoke(): Boolean
}