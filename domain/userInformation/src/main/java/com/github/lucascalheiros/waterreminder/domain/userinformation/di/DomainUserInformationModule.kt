package com.github.lucascalheiros.waterreminder.domain.userinformation.di

import com.github.lucascalheiros.waterreminder.data.themewrapper.di.themeWrapperModule


val domainUserInformationModule = listOf(useCaseModule, repositoryModule) + themeWrapperModule