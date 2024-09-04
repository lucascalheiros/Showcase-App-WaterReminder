package com.github.lucascalheiros.waterreminder.shared.di

import com.github.lucascalheiros.waterreminder.data.firstaccessdataprovider.di.firstAccessDataProviderModule
import com.github.lucascalheiros.waterreminder.data.home.di.homeDataModule
import com.github.lucascalheiros.waterreminder.data.measuresystemprovider.di.measureSystemProviderModule
import com.github.lucascalheiros.waterreminder.data.notificationprovider.di.notificationProviderModule
import com.github.lucascalheiros.waterreminder.data.themewrapper.di.themeDataProviderModule
import com.github.lucascalheiros.waterreminder.data.userprofileprovider.di.userProfileProviderModule
import com.github.lucascalheiros.waterreminder.data.waterdataprovider.di.waterDataProviderModule
import com.github.lucascalheiros.waterreminder.domain.firstaccess.di.firstAccessDomainModule
import com.github.lucascalheiros.waterreminder.domain.history.di.historyDomainModule
import com.github.lucascalheiros.waterreminder.domain.home.di.homeDomainModule
import com.github.lucascalheiros.waterreminder.domain.remindnotifications.di.domainRemindNotificationsModule
import com.github.lucascalheiros.waterreminder.domain.userinformation.di.domainUserInformationModule
import com.github.lucascalheiros.waterreminder.domain.watermanagement.di.domainWaterManagementModule
import com.github.lucascalheiros.waterreminder.measuresystem.di.domainMeasureSystemModule


private val dataModules = notificationProviderModule +
        firstAccessDataProviderModule +
        themeDataProviderModule +
        userProfileProviderModule +
        waterDataProviderModule +
        measureSystemProviderModule +
        homeDataModule

private val domainModules = domainRemindNotificationsModule +
        domainMeasureSystemModule +
        domainWaterManagementModule +
        domainUserInformationModule +
        firstAccessDomainModule +
        homeDomainModule +
        historyDomainModule

val appDataModule = dataModules +
        dispatchersQualifierModule +
        domainModules