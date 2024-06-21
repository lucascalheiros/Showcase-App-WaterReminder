package com.github.lucascalheiros.waterreminder.domain.firstaccess.domain.usecases

interface SetFirstAccessNotificationStateUseCase {
    suspend operator fun invoke(value: Boolean)
}