package com.github.lucascalheiros.waterreminder.domain.watermanagement.di

import com.github.lucascalheiros.waterreminder.data.waterdataprovider.di.waterDataProviderModule
import com.github.lucascalheiros.waterreminder.measuresystem.di.domainMeasureSystemModule


val domainWaterManagementModule = listOf(repositoryModule, useCaseModule) +
        waterDataProviderModule +
        domainMeasureSystemModule