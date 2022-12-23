package com.example.clubben.ui.app

import com.example.clubben.domain.auth.Account
import com.example.clubben.domain.auth.GetCurrentAccountUseCase
import com.rickclephas.kmm.viewmodel.KMMViewModel
import com.rickclephas.kmm.viewmodel.MutableStateFlow
import com.rickclephas.kmm.viewmodel.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AppViewModel(
    private val getCurrentAccountUseCase: GetCurrentAccountUseCase
) : KMMViewModel() {
    var account: MutableStateFlow<Account?> = MutableStateFlow(viewModelScope, null)
    var showAuthFlow = MutableStateFlow(viewModelScope, false)

    init {
        viewModelScope.coroutineScope.launch {
            val current = getCurrentAccountUseCase()
            account.update { current }
            showAuthFlow.update { current == null }
        }
    }

    fun setAccount(account: Account?) {
        this.account.update { account }
    }

    fun showAuthFlow() {
        showAuthFlow.update { true }
    }

    fun dismissAuthFlow() {
        showAuthFlow.update { false }
    }
}