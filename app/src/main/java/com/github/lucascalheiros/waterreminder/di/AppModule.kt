package com.github.lucascalheiros.waterreminder.di

import com.github.lucascalheiros.waterreminder.data.firstaccessdataprovider.di.firstAccessDataProviderModule
import com.github.lucascalheiros.waterreminder.data.home.di.homeDataModule
import com.github.lucascalheiros.waterreminder.data.measuresystemprovider.di.measureSystemProviderModule
import com.github.lucascalheiros.waterreminder.data.notificationprovider.di.notificationProviderModule
import com.github.lucascalheiros.waterreminder.data.themewrapper.di.themeDataProviderModule
import com.github.lucascalheiros.waterreminder.data.userprofileprovider.di.userProfileProviderModule
import com.github.lucascalheiros.waterreminder.data.waterdataprovider.di.waterDataProviderModule
import com.github.lucascalheiros.waterreminder.feature.firstaccess.di.firstAccessModule
import com.github.lucascalheiros.waterreminder.feature.history.di.historyModule
import com.github.lucascalheiros.waterreminder.feature.home.di.homeModule
import com.github.lucascalheiros.waterreminder.feature.settings.di.settingsModule

private val featureModules = homeModule +
        historyModule +
        settingsModule +
        firstAccessModule

private val dataModules = notificationProviderModule +
        firstAccessDataProviderModule +
        themeDataProviderModule +
        userProfileProviderModule +
        waterDataProviderModule +
        measureSystemProviderModule +
        homeDataModule

val appModule = featureModules +
        dataModules +
        dispatchersQualifierModule