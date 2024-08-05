package com.github.lucascalheiros.waterreminder.domain.userinformation.domain.usecases

import com.github.lucascalheiros.waterreminder.domain.userinformation.domain.models.UserProfile
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines
import kotlinx.coroutines.flow.Flow

interface GetUserProfileUseCase {
    @NativeCoroutines
    operator fun invoke(): Flow<UserProfile>
    @NativeCoroutines
    suspend fun single(): UserProfile
}