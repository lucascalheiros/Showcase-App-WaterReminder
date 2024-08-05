package com.github.lucascalheiros.waterreminder.domain.firstaccess.domain.usecases

import com.rickclephas.kmp.nativecoroutines.NativeCoroutines

interface IsFirstAccessCompletedUseCase {
    @NativeCoroutines
    suspend operator fun invoke(): Boolean
}