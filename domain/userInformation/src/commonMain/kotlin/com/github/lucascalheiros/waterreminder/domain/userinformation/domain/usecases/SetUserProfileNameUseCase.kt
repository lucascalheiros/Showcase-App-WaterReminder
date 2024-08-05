package com.github.lucascalheiros.waterreminder.domain.userinformation.domain.usecases

import com.rickclephas.kmp.nativecoroutines.NativeCoroutines

interface SetUserProfileNameUseCase {
    @NativeCoroutines
    suspend operator fun invoke(name: String)
}