package com.example.clubben.repository

import com.example.clubben.db.ClubbenDatabase
import com.example.clubben.di.ClubbenDatabaseWrapper
import com.squareup.sqldelight.android.AndroidSqliteDriver
import org.koin.dsl.module

actual fun platformModule() = module {
    single {
        val driver = AndroidSqliteDriver(ClubbenDatabase.Schema, get(), "clubben.db")
        ClubbenDatabaseWrapper(ClubbenDatabase(driver))
    }
}