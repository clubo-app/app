package com.example.clubben.ui.signup

import com.example.clubben.ui.app.AppViewModel
import com.example.clubben.usecases.auth.RegisterUserCase
import com.example.clubben.usecases.profile.UsernameTakenUseCase
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

open class SignUpViewModel : KMMViewModel(), KoinComponent {
    private val registerUserCase: RegisterUserCase by inject()
    private val appViewModel: AppViewModel by inject()
    private val usernameTakenUseCase: UsernameTakenUseCase by inject()

    @NativeCoroutinesState
    var email = MutableStateFlow(viewModelScope, "")

    @NativeCoroutinesState
    var username = MutableStateFlow(viewModelScope, "")

    @NativeCoroutinesState
    var firstname = MutableStateFlow(viewModelScope, "")

    @NativeCoroutinesState
    var lastname = MutableStateFlow(viewModelScope, "")

    @NativeCoroutinesState
    var password = MutableStateFlow(viewModelScope, "")

    @NativeCoroutinesState
    var emailValid = MutableStateFlow(viewModelScope, false)

    private var passwordValid = MutableStateFlow(viewModelScope, false)

    @NativeCoroutinesState
    var usernameTaken = MutableStateFlow(viewModelScope, true)

    @NativeCoroutinesState
    var showPassword = MutableStateFlow(viewModelScope, false)

    @NativeCoroutinesState
    var signUpLoading = MutableStateFlow(viewModelScope, false)

    @NativeCoroutinesState
    var showPasswordError = combine(password, passwordValid) { pw, isValid ->
        pw != "" && !isValid
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), false)

    @NativeCoroutinesState
    var signUpButtonDisabled =
        combine(
            firstname,
            username,
            usernameTaken,
            emailValid,
            passwordValid
        ) { fn, un, uTaken, eValid, pValid ->
            fn == "" || un == "" || uTaken || !eValid || !pValid
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), true)

    init {
        viewModelScope.coroutineScope.launch {
            username.collect { username ->
                if (username != "") {
                    usernameTakenUseCase(username).fold(
                        onSuccess = { taken ->
                            usernameTaken.update { taken }
                        },
                        onFailure = {
                            usernameTaken.update { true }
                        }
                    )
                }
            }
        }
    }

    fun changeEmail(email: String) {
        this.emailValid.update { emailPattern.matches(email) }
        this.email.update { email }
    }

    fun changeUsername(username: String) {
        this.username.update { username }
    }

    fun changeFirstname(firstname: String) {
        this.firstname.update { firstname }
    }

    fun changeLastname(lastname: String) {
        this.lastname.update { lastname }
    }

    fun changePassword(password: String) {
        this.passwordValid.update { password.length >= 8 }
        this.password.update { password }
    }

    fun toggleShowPassword() {
        this.showPassword.update { !it }
    }

    fun signUp() {
        viewModelScope.coroutineScope.launch {
            signUpLoading.update { true }
            val res = registerUserCase(
                email.value,
                password.value,
                username.value,
                firstname.value,
                lastname.value,
                null
            )
            res.fold(
                onSuccess = {
                    appViewModel.setAccount(it)
                    appViewModel.dismissAuthFlow()
                },
                onFailure = {
                    appViewModel.setAccount(null)
                }
            )
            signUpLoading.update { false }
        }
    }
}