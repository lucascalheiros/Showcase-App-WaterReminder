package com.github.lucascalheiros.waterreminder.domain.userinformation.domain.usecases.impl

import com.github.lucascalheiros.waterreminder.domain.userinformation.domain.models.AmbienceTemperatureLevel
import com.github.lucascalheiros.waterreminder.domain.userinformation.domain.repositories.UserProfileRepository
import com.github.lucascalheiros.waterreminder.domain.userinformation.domain.usecases.SetUserProfileTemperatureLevelUseCase
import kotlinx.coroutines.flow.first

class SetUserProfileTemperatureLevelUseCaseImpl(
    private val userProfileRepository: UserProfileRepository
) : SetUserProfileTemperatureLevelUseCase {
    override suspend fun invoke(temperatureLevel: AmbienceTemperatureLevel) {
        userProfileRepository.getUserProfile().first().copy(
            temperatureLevel = temperatureLevel
        ).also {
            userProfileRepository.setUserProfile(it)
        }
    }
}