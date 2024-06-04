package com.github.lucascalheiros.waterreminder.data.userprofileprovider.datasources.local.dao

import com.github.lucascalheiros.waterreminder.data.userprofileprovider.models.UserProfileDb
import kotlinx.coroutines.flow.Flow


interface UserProfileDao {
    suspend fun getUserProfile(): UserProfileDb?
    fun getUserProfileFlow(): Flow<UserProfileDb?>
    suspend fun save(userProfileDb: UserProfileDb)
}