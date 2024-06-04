package com.github.lucascalheiros.waterreminder.domain.userinformation.data.repositories

import com.github.lucascalheiros.waterreminder.data.userprofileprovider.datasources.local.dao.UserProfileDao
import com.github.lucascalheiros.waterreminder.domain.userinformation.data.converters.toDb
import com.github.lucascalheiros.waterreminder.domain.userinformation.data.converters.toModel
import com.github.lucascalheiros.waterreminder.domain.userinformation.domain.models.UserProfile
import com.github.lucascalheiros.waterreminder.domain.userinformation.domain.repositories.UserProfileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map

internal class UserProfileRepositoryImpl(
    private val userProfileDao: UserProfileDao
): UserProfileRepository {
    override fun getUserProfile(): Flow<UserProfile> {
        return userProfileDao.getUserProfileFlow().filterNotNull().map { it.toModel() }
    }

    override suspend fun setUserProfile(userProfile: UserProfile) {
        userProfileDao.save(userProfile.toDb())
    }
}