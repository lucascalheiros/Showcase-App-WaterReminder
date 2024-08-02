package com.github.lucascalheiros.waterreminder.data.waterdataprovider.data.repositories.datasources

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import co.touchlab.sqliter.DatabaseConfiguration
import com.github.lucascalheiros.waterreminder.data.waterdataprovider.WaterDatabase

internal fun createDriver(): SqlDriver {
    return NativeSqliteDriver(
        WaterDatabase.Schema,
        waterDatabaseName,
        onConfiguration = { config: DatabaseConfiguration ->
            config.copy(
                extendedConfig = DatabaseConfiguration.Extended(foreignKeyConstraints = true)
            )
        })
}