package com.example.clubben.ui.signup

import com.example.clubben.domain.auth.RegisterUserCase
import com.example.clubben.domain.profile.UsernameExistsUseCase
import com.example.clubben.ui.app.AppViewModel
import com.example.clubben.utils.DataState
import com.rickclephas.kmm.viewmodel.KMMViewModel
import com.rickclephas.kmm.viewmodel.MutableStateFlow
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import kotlinx.coroutines.flow.update
import org.koin.core.component.KoinComponent

class SignupViewModel(
    private val registerUserCase: RegisterUserCase,
    private val appViewModel: AppViewModel,
    private val usernameExistsUseCase: UsernameExistsUseCase
) : KMMViewModel(), KoinComponent {
    private val emailPattern = Regex(
        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+"
    );

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

    @NativeCoroutinesState
    var passwordValid = MutableStateFlow(viewModelScope, false)

    @NativeCoroutinesState
    var usernameTaken = MutableStateFlow(viewModelScope, false)

    @NativeCoroutinesState
    var showPassword = MutableStateFlow(viewModelScope, false)

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

    fun changePassword(password: String) {
        this.passwordValid.update { password.length >= 8 }
        this.password.update { password }
    }

    fun toggleShowPassword() {
        this.showPassword.update { !it }
    }

    @NativeCoroutines
    suspend fun checkUsername(): Boolean {
        return usernameExistsUseCase(username.value)
    }

    @NativeCoroutines
    suspend fun signUp() {
        val res = registerUserCase(
            email.value,
            password.value,
            username.value,
            firstname.value,
            lastname.value,
            null
        )
        when (res) {
            is DataState.Success -> {
                appViewModel.setAccount(res.value)
                appViewModel.dismissAuthFlow()
            }
            is DataState.Failure -> {
                appViewModel.setAccount(null)
            }
        }
    }
}