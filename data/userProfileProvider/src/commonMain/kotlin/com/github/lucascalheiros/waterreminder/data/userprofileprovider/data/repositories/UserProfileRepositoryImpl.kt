package com.github.lucascalheiros.waterreminder.data.userprofileprovider.data.repositories

import com.github.lucascalheiros.waterreminder.data.userprofileprovider.data.repositories.datasources.UserProfileDataSource
import com.github.lucascalheiros.waterreminder.data.userprofileprovider.data.converters.toDb
import com.github.lucascalheiros.waterreminder.data.userprofileprovider.data.converters.toModel
import com.github.lucascalheiros.waterreminder.domain.userinformation.domain.models.UserProfile
import com.github.lucascalheiros.waterreminder.domain.userinformation.domain.repositories.UserProfileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class UserProfileRepositoryImpl(
    private val userProfileDatasource: UserProfileDataSource
): UserProfileRepository {
    override fun getUserProfile(): Flow<UserProfile> {
        return userProfileDatasource.getUserProfileFlow().map { it.toModel() }
    }

    override suspend fun setUserProfile(userProfile: UserProfile) {
        userProfileDatasource.save(userProfile.toDb())
    }
}