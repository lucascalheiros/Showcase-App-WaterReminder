package com.github.lucascalheiros.watertreminder.domain.firstaccess.domain.usecases

interface IsDailyIntakeFirstSetUseCase {
    suspend operator fun invoke(): Boolean
}