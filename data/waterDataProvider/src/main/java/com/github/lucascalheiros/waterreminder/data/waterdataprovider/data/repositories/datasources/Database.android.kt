package com.github.lucascalheiros.waterreminder.data.waterdataprovider.data.repositories.datasources

import android.content.Context
import androidx.sqlite.db.SupportSQLiteDatabase
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.github.lucascalheiros.waterreminder.data.waterdataprovider.WaterDatabase

internal fun createDriver(context: Context): SqlDriver {
    return AndroidSqliteDriver(
        WaterDatabase.Schema,
        context,
        waterDatabaseName,
        callback =  object : AndroidSqliteDriver.Callback(WaterDatabase.Schema) {
            override fun onOpen(db: SupportSQLiteDatabase) {
                db.setForeignKeyConstraintsEnabled(true)
            }
        })
}