package com.turkcell.lyraapp.ui.register

import com.turkcell.lyraapp.ui.base.UiEffect
import com.turkcell.lyraapp.ui.base.UiEvent
import com.turkcell.lyraapp.ui.base.UiState

object RegisterContract {


    data class State(
        val firstName: String = "",
        val lastName: String = "",
        val phone: String = "",
        val password: String = "",
        val isTermsAccepted: Boolean = false,
        val isLoading: Boolean = false
    ) : UiState


    sealed interface Event : UiEvent {
        data class OnFirstNameChanged(val name: String) : Event
        data class OnLastNameChanged(val lastName: String) : Event
        data class OnPhoneChanged(val phone: String) : Event
        data class OnPasswordChanged(val password: String) : Event
        data class OnTermsAcceptedChanged(val isAccepted: Boolean) : Event

        object OnRegisterClicked : Event
        object OnLoginClicked : Event
    }

    sealed interface Effect : UiEffect {
        object NavigateToHome : Effect
        object NavigateToLogin : Effect
        data class ShowError(val message: String) : Effect
    }
}