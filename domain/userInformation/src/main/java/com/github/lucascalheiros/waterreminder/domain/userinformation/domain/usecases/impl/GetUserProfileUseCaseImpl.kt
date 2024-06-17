package com.github.lucascalheiros.waterreminder.domain.userinformation.domain.usecases.impl

import com.github.lucascalheiros.waterreminder.domain.userinformation.domain.models.UserProfile
import com.github.lucascalheiros.waterreminder.domain.userinformation.domain.repositories.UserProfileRepository
import com.github.lucascalheiros.waterreminder.domain.userinformation.domain.usecases.GetUserProfileUseCase
import com.github.lucascalheiros.waterreminder.measuresystem.domain.usecases.GetWeightUnitUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first

class GetUserProfileUseCaseImpl(
    private val userProfileRepository: UserProfileRepository,
    private val getWeightUnitUseCase: GetWeightUnitUseCase
): GetUserProfileUseCase {
    override fun invoke(): Flow<UserProfile> {
        return combine(userProfileRepository.getUserProfile(), getWeightUnitUseCase()) { profile, unit ->
            profile.copy(weight = profile.weight.toUnit(unit))
        }
    }

    override suspend fun single(): UserProfile {
        return userProfileRepository.getUserProfile().first().let {
            it.copy(weight = it.weight.toUnit(getWeightUnitUseCase.single()))
        }
    }
}