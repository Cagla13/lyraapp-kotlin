package com.turkcell.lyraapp.ui.login

import com.turkcell.lyraapp.ui.base.UiEffect
import com.turkcell.lyraapp.ui.base.UiEvent
import com.turkcell.lyraapp.ui.base.UiState

object LoginContract {

    data class State(
        val phone: String = "",
        val password: String = "",
        val isLoading: Boolean = false
    ) : UiState

    sealed interface Event : UiEvent {
        data class OnPhoneChanged(val phone: String) : Event
        data class OnPasswordChanged(val password: String) : Event
        data object OnLoginClicked : Event
        data object OnRegisterClicked : Event
    }

    sealed interface Effect : UiEffect {
        data object NavigateToHome : Effect
        data object NavigateToRegister : Effect
        data class ShowError(val message: String) : Effect
    }
}