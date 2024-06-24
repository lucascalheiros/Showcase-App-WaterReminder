package com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.impl

import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.models.DefaultAddDrinkInfo
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.models.ThemeAwareColor
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.GetDefaultAddDrinkInfoUseCase

internal class GetDefaultAddDrinkInfoUseCaseImpl: GetDefaultAddDrinkInfoUseCase {
    override suspend fun invoke(): DefaultAddDrinkInfo {
        return DefaultAddDrinkInfo(
            1f,
            ThemeAwareColor(0xff0000f4.toInt(), 0xff26C6DA.toInt())
        )
    }
}
