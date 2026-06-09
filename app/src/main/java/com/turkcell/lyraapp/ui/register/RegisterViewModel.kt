package com.turkcell.lyraapp.ui.register

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

class RegisterViewModel : ViewModel() {

    private val repository = FakeAuthRepository()

    private val _state = MutableStateFlow(RegisterContract.State())
    val state: StateFlow<RegisterContract.State> = _state.asStateFlow()

    private val _effect = MutableSharedFlow<RegisterContract.Effect>()
    val effect: SharedFlow<RegisterContract.Effect> = _effect.asSharedFlow()

    fun onEvent(event: RegisterContract.Event) {
        when (event) {
            is RegisterContract.Event.OnFirstNameChanged -> {
                _state.update { it.copy(firstName = event.name) }
            }
            is RegisterContract.Event.OnLastNameChanged -> {
                _state.update { it.copy(lastName = event.lastName) }
            }
            is RegisterContract.Event.OnPhoneChanged -> {
                _state.update { it.copy(phone = event.phone) }
            }
            is RegisterContract.Event.OnPasswordChanged -> {
                _state.update { it.copy(password = event.password) }
            }
            is RegisterContract.Event.OnTermsAcceptedChanged -> {
                _state.update { it.copy(isTermsAccepted = event.isAccepted) }
            }
            is RegisterContract.Event.OnLoginClicked -> {
                emitEffect(RegisterContract.Effect.NavigateToLogin)
            }
            is RegisterContract.Event.OnRegisterClicked -> {
                performRegistration()
            }
        }
    }


    private fun performRegistration() {
        val currentState = state.value


        if (!currentState.isTermsAccepted) {
            emitEffect(RegisterContract.Effect.ShowError("Kullanım Koşullarını kabul etmelisiniz."))
            return
        }


        _state.update { it.copy(isLoading = true) }

        viewModelScope.launch {

            val result = repository.register(
                firstName = currentState.firstName,
                lastName = currentState.lastName,
                phone = currentState.phone,
                password = currentState.password
            )


            _state.update { it.copy(isLoading = false) }


            result.onSuccess {
                emitEffect(RegisterContract.Effect.NavigateToHome)
            }.onFailure { error ->
                emitEffect(RegisterContract.Effect.ShowError(error.message ?: "Bilinmeyen hata"))
            }
        }
    }

    private fun emitEffect(effect: RegisterContract.Effect) {
        viewModelScope.launch {
            _effect.emit(effect)
        }
    }
}