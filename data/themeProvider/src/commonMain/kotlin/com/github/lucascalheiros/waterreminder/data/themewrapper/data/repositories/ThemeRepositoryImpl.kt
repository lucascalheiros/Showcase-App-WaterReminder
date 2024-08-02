package com.github.lucascalheiros.waterreminder.data.themewrapper.data.repositories

import com.github.lucascalheiros.waterreminder.data.themewrapper.data.repositories.datasources.ThemeDataSource
import com.github.lucascalheiros.waterreminder.data.themewrapper.data.converters.toAppTheme
import com.github.lucascalheiros.waterreminder.data.themewrapper.data.converters.toThemeOptions
import com.github.lucascalheiros.waterreminder.domain.userinformation.domain.models.AppTheme
import com.github.lucascalheiros.waterreminder.domain.userinformation.domain.repositories.ThemeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class ThemeRepositoryImpl(
    private val themeDataSource: ThemeDataSource
): ThemeRepository {

    override fun getTheme(): Flow<AppTheme> = themeDataSource.getTheme().map { it.toAppTheme() }

    override suspend fun setTheme(appTheme: AppTheme) = themeDataSource.setTheme(appTheme.toThemeOptions())

}