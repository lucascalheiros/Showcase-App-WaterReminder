package com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases

import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.models.DefaultAddDrinkInfo

interface GetDefaultAddDrinkInfoUseCase {
    suspend operator fun invoke(): DefaultAddDrinkInfo
}
