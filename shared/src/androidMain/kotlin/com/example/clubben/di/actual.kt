package com.example.clubben.di

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.example.clubben.db.ClubbenDatabase
import org.koin.dsl.module

actual fun platformModule() = module {
    single<SqlDriver> {
        AndroidSqliteDriver(ClubbenDatabase.Schema, get(), "clubben.db")
    }
}