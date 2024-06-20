package com.github.lucascalheiros.waterreminder.domain.userinformation.di

import com.github.lucascalheiros.waterreminder.measuresystem.di.domainMeasureSystemModule


val domainUserInformationModule = listOf(useCaseModule) + domainMeasureSystemModule