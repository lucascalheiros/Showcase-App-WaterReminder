package com.github.lucascalheiros.waterreminder.domain.firstaccess.domain.usecases

interface IsDailyIntakeFirstSetUseCase {
    suspend operator fun invoke(): Boolean
}