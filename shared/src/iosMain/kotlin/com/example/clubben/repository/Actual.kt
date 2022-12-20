package com.example.clubben.repository

import com.example.clubben.db.ClubbenDatabase
import com.example.clubben.di.ClubbenDatabaseWrapper
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver
import org.koin.dsl.module

actual fun platformModule() = module {
    single {
        val driver = NativeSqliteDriver(ClubbenDatabase.Schema, "clubben.db")
        ClubbenDatabaseWrapper(ClubbenDatabase(driver))
    }
}