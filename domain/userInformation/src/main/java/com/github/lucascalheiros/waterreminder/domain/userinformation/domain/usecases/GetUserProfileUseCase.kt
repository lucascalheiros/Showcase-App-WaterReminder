package com.github.lucascalheiros.waterreminder.domain.userinformation.domain.usecases

import com.github.lucascalheiros.waterreminder.domain.userinformation.domain.models.UserProfile
import kotlinx.coroutines.flow.Flow

interface GetUserProfileUseCase {
    operator fun invoke(): Flow<UserProfile>
    suspend fun single(): UserProfile
}