package com.example.clubben.data.repository.profiles

import com.example.clubben.di.ClubbenDatabaseWrapper
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ProfilesRepositoryImpl : KoinComponent {
    private val clubbenDatabase: ClubbenDatabaseWrapper by inject()
    private val profileQueries = clubbenDatabase.instance?.profileQueries

}