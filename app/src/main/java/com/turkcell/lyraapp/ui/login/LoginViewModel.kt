package com.turkcell.lyraapp.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.turkcell.lyraapp.data.repository.FakeAuthRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val repository = FakeAuthRepository()

    private val _state = MutableStateFlow(LoginContract.State())
    val state: StateFlow<LoginContract.State> = _state.asStateFlow()

    private val _effect = MutableSharedFlow<LoginContract.Effect>()
    val effect: SharedFlow<LoginContract.Effect> = _effect.asSharedFlow()

    fun onEvent(event: LoginContract.Event) {
        when (event) {
            is LoginContract.Event.OnPhoneChanged -> {
                _state.update { it.copy(phone = event.phone) }
            }
            is LoginContract.Event.OnPasswordChanged -> {
                _state.update { it.copy(password = event.password) }
            }
            is LoginContract.Event.OnRegisterClicked -> {
                emitEffect(LoginContract.Effect.NavigateToRegister)
            }
            is LoginContract.Event.OnLoginClicked -> {
                performLogin()
            }
        }
    }

    private fun performLogin() {
        val currentState = state.value

        _state.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            val result = repository.login(
                phone = currentState.phone,
                password = currentState.password
            )

            _state.update { it.copy(isLoading = false) }

            result.onSuccess {
                emitEffect(LoginContract.Effect.NavigateToHome)
            }.onFailure { error ->
                emitEffect(LoginContract.Effect.ShowError(error.message ?: "Bilinmeyen hata"))
            }
        }
    }

    private fun emitEffect(effect: LoginContract.Effect) {
        viewModelScope.launch {
            _effect.emit(effect)
        }
    }
}