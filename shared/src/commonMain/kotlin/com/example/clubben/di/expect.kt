package com.example.clubben.di

import app.cash.sqldelight.adapter.primitive.FloatColumnAdapter
import app.cash.sqldelight.adapter.primitive.IntColumnAdapter
import app.cash.sqldelight.db.SqlDriver
import com.example.clubben.db.ClubbenDatabase
import com.example.clubben.db.LocalParty
import com.example.clubben.db.LocalProfile
import org.koin.core.module.Module

expect fun platformModule(): Module

fun createDatabase(driver: SqlDriver): ClubbenDatabase {
    return ClubbenDatabase(
        driver,
        LocalPartyAdapter = LocalParty.Adapter(
            max_participantsAdapter = IntColumnAdapter,
            favorite_countAdapter = IntColumnAdapter,
            latAdapter = FloatColumnAdapter,
            lonAdapter = FloatColumnAdapter,
            participation_countAdapter = IntColumnAdapter
        ),
        LocalProfileAdapter = LocalProfile.Adapter(
            friend_countAdapter = IntColumnAdapter
        )
    )
}
