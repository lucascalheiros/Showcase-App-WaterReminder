package com.github.lucascalheiros.waterreminder.domain.userinformation.domain.repositories

import com.github.lucascalheiros.waterreminder.domain.userinformation.domain.models.UserProfile
import kotlinx.coroutines.flow.Flow

interface UserProfileRepository {
    fun getUserProfile(): Flow<UserProfile>
    suspend fun setUserProfile(userProfile: UserProfile)
}