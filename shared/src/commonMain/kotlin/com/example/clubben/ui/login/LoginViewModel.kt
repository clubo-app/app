package com.example.clubben.ui.login

import com.example.clubben.domain.auth.LoginUseCase
import com.example.clubben.ui.app.AppViewModel
import com.example.clubben.utils.DataState
import com.rickclephas.kmm.viewmodel.KMMViewModel
import com.rickclephas.kmm.viewmodel.MutableStateFlow
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import kotlinx.coroutines.flow.update
import org.koin.core.component.KoinComponent

open class LoginViewModel(
    private val loginUseCase: LoginUseCase,
    private val appViewModel: AppViewModel
) : KMMViewModel(), KoinComponent {
    @NativeCoroutinesState
    var email = MutableStateFlow(viewModelScope, "")

    @NativeCoroutinesState
    var password = MutableStateFlow(viewModelScope, "")

    @NativeCoroutinesState
    var showPassword = MutableStateFlow(viewModelScope, false)

    fun changeEmail(email: String) {
        this.email.update { email }
    }

    fun changePassword(password: String) {
        this.password.update { password }
    }

    fun toggleShowPassword() {
        showPassword.update { !it }
    }

    suspend fun login() {
        val res = loginUseCase(email.value, password.value)
        when (res) {
            is DataState.Success -> {
                appViewModel.dismissAuthFlow()
                appViewModel.setAccount(res.value)
            }
            is DataState.Failure -> {
                appViewModel.setAccount(null)
            }
        }
    }
}