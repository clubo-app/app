package com.example.clubben.ui.onboarding

import com.example.clubben.ui.app.AppViewModel
import com.rickclephas.kmm.viewmodel.KMMViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

open class OnboardingViewModel : KMMViewModel(), KoinComponent {
    private val appViewModel: AppViewModel by inject()

    fun skipAuth() {
        appViewModel.dismissAuthFlow()
    }
}