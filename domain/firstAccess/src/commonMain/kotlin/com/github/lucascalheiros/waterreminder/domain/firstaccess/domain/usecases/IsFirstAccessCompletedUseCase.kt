package com.github.lucascalheiros.waterreminder.domain.firstaccess.domain.usecases

import com.rickclephas.kmp.nativecoroutines.NativeCoroutines
import kotlinx.coroutines.flow.Flow

interface IsFirstAccessCompletedUseCase {
    @NativeCoroutines
    suspend fun single(): Boolean

    @NativeCoroutines
    operator fun invoke(): Flow<Boolean>
}