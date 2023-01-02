package com.example.clubben.ui.app

import com.example.clubben.usecases.auth.Account
import com.example.clubben.usecases.auth.GetCurrentAccountUseCase
import com.example.clubben.usecases.auth.SignOutUseCase
import com.rickclephas.kmm.viewmodel.KMMViewModel
import com.rickclephas.kmm.viewmodel.MutableStateFlow
import com.rickclephas.kmm.viewmodel.coroutineScope
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

// used in ios to inject singleton
class AppViewModelHelper : KoinComponent {
    private val vm: AppViewModel by inject()

    fun vm(): AppViewModel {
        println("Getting AppViewmodel singleton instance.")
        return vm
    }
}

open class AppViewModel : KMMViewModel(), KoinComponent {
    private val getCurrentAccountUseCase: GetCurrentAccountUseCase by inject()
    private val signOutUseCase: SignOutUseCase by inject()

    @NativeCoroutinesState
    var account: MutableStateFlow<Account?> = MutableStateFlow(viewModelScope, null)

    @NativeCoroutinesState
    var showAuthFlow = MutableStateFlow(viewModelScope, false)

    init {
        viewModelScope.coroutineScope.launch {
            val current = getCurrentAccountUseCase()
            current.fold(
                onSuccess = { acc ->
                    if (acc != null) {
                        account.update { acc }
                        showAuthFlow.update { false }
                    } else {
                        dismissAuthFlow()
                    }
                },
                onFailure = {
                    showAuthFlow.update { true }
                }
            )
        }
    }

    fun setAccount(account: Account?) {
        this.account.update { account }
    }

    fun setAuthFlow(show: Boolean) {
        showAuthFlow.update { show }
    }

    fun dismissAuthFlow() {
        showAuthFlow.update { false }
    }

    fun signOut() {
        viewModelScope.coroutineScope.launch {
            signOutUseCase()
            setAuthFlow(true)
            setAccount(null)
        }
    }
}