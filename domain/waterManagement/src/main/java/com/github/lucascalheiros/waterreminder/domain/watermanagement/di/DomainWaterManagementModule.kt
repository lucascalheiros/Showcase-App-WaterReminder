package com.github.lucascalheiros.waterreminder.domain.watermanagement.di

import com.github.lucascalheiros.waterreminder.measuresystem.di.domainMeasureSystemModule


val domainWaterManagementModule = listOf(useCaseModule) + domainMeasureSystemModule