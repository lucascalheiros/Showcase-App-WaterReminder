package com.github.lucascalheiros.waterreminder.domain.userinformation.domain.usecases

interface SetUserProfileNameUseCase {
    suspend operator fun invoke(name: String)
}