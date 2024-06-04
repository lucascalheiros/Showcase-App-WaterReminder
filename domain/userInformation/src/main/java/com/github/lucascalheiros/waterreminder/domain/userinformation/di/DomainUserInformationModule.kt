package com.github.lucascalheiros.waterreminder.domain.userinformation.di

import com.github.lucascalheiros.waterreminder.data.themewrapper.di.themeWrapperModule
import com.github.lucascalheiros.waterreminder.data.userprofileprovider.di.userProfileProviderModule
import com.github.lucascalheiros.waterreminder.measuresystem.di.domainMeasureSystemModule


val domainUserInformationModule = listOf(useCaseModule, repositoryModule) +
        themeWrapperModule +
        userProfileProviderModule +
        domainMeasureSystemModule