package com.github.lucascalheiros.waterremindermvp.domain.userinformation.di

import com.github.lucascalheiros.waterremindermvp.data.themewrapper.di.themeWrapperModule


val domainUserInformationModule = listOf(useCaseModule, repositoryModule) + themeWrapperModule