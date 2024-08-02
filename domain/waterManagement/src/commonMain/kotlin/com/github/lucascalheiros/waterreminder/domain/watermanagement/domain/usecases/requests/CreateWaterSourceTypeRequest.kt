package com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.requests

import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.models.ThemeAwareColor

data class CreateWaterSourceTypeRequest(
    val name: String,
    val themeAwareColor: ThemeAwareColor,
    val hydrationFactor: Float
)
