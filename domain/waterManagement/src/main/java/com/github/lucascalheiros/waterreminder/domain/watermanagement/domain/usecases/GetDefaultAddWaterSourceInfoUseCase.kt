package com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases

import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.models.DefaultAddWaterSourceInfo

interface GetDefaultAddWaterSourceInfoUseCase {
    suspend operator fun invoke(): DefaultAddWaterSourceInfo
}
