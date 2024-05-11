package com.github.lucascalheiros.waterremindermvp.domain.watermanagement.domain.usecases

import com.github.lucascalheiros.waterremindermvp.domain.watermanagement.domain.models.DefaultAddWaterSourceInfo

interface GetDefaultAddWaterSourceInfoUseCase {
    suspend operator fun invoke(): DefaultAddWaterSourceInfo
}
