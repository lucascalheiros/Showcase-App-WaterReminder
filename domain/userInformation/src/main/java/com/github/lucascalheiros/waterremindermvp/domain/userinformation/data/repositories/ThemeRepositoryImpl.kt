package com.github.lucascalheiros.waterremindermvp.domain.userinformation.data.repositories

import com.github.lucascalheiros.waterremindermvp.data.themewrapper.framework.ThemeWrapper
import com.github.lucascalheiros.waterremindermvp.domain.userinformation.data.converters.toAppTheme
import com.github.lucascalheiros.waterremindermvp.domain.userinformation.data.converters.toThemeOptions
import com.github.lucascalheiros.waterremindermvp.domain.userinformation.domain.models.AppTheme
import com.github.lucascalheiros.waterremindermvp.domain.userinformation.domain.repositories.ThemeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ThemeRepositoryImpl(
    private val themeWrapper: ThemeWrapper
): ThemeRepository {

    override fun getTheme(): Flow<AppTheme> = themeWrapper.getTheme().map { it.toAppTheme() }

    override fun setTheme(appTheme: AppTheme) = themeWrapper.setTheme(appTheme.toThemeOptions())

}