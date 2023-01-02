package com.example.clubben.ui.login

import com.example.clubben.ui.app.AppViewModel
import com.example.clubben.usecases.auth.LoginUseCase
import com.example.clubben.utils.emailPattern
import com.rickclephas.kmm.viewmodel.KMMViewModel
import com.rickclephas.kmm.viewmodel.MutableStateFlow
import com.rickclephas.kmm.viewmodel.coroutineScope
import com.rickclephas.kmm.viewmodel.stateIn
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

open class LoginViewModel : KMMViewModel(), KoinComponent {
    private val loginUseCase: LoginUseCase by inject()
    private val appViewModel: AppViewModel by inject()

    @NativeCoroutinesState
    var email = MutableStateFlow(viewModelScope, "")

    @NativeCoroutinesState
    var emailValid = MutableStateFlow(viewModelScope, false)

    @NativeCoroutinesState
    var password = MutableStateFlow(viewModelScope, "")

    @NativeCoroutinesState
    var showPassword = MutableStateFlow(viewModelScope, false)

    @NativeCoroutinesState
    var passwordValid = MutableStateFlow(viewModelScope, false)

    @NativeCoroutinesState
    var loginLoading = MutableStateFlow(viewModelScope, false)

    @NativeCoroutinesState
    var loginButtonDisabled = combine(emailValid, passwordValid) { eValid, pValid ->
        !eValid || !pValid
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        true
    )

    fun changeEmail(email: String) {
        this.emailValid.update { emailPattern.matches(email) }
        this.email.update { email }
    }

    fun changePassword(password: String) {
        this.passwordValid.update { password.length >= 8 }
        this.password.update { password }
    }

    fun toggleShowPassword() {
        showPassword.update { !it }
    }

    fun login() {
        viewModelScope.coroutineScope.launch {
            loginLoading.update { true }
            val res = loginUseCase(email.value, password.value)
            res.fold(
                onSuccess = {
                    println(it)
                    appViewModel.dismissAuthFlow()
                    appViewModel.setAccount(it)
                },
                onFailure = {
                    appViewModel.setAccount(null)
                }
            )
            loginLoading.update { false }
        }
    }
}