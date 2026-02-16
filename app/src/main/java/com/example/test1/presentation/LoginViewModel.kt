package com.example.test1.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update

// -------------------- VIEWMODEL (evento one-shot) --------------------
sealed interface LoginEvent {
    data object GoHome : LoginEvent
    data class ShowError(val message: String) : LoginEvent
}

data class DiceUiState(
    val firstDieValue: Int? = null,
    val secondDieValue: Int? = null,
    val numberOfRolls: Int = 0,
)
class LoginViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(DiceUiState())
    val uiState: StateFlow<DiceUiState> = _uiState.asStateFlow()

    fun login(user: String, pass: String) {
        // Demo: valida básico
        if (user.trim().isNotEmpty() && pass.length >= 4) {
//            _events.trySend(LoginEvent.GoHome)
        } else {
//            _events.trySend(LoginEvent.ShowError("Credenciales inválidas"))
        }
        _uiState.update {
            it.copy(
                firstDieValue = (1..6).random(),
                secondDieValue = (1..6).random(),
                numberOfRolls = it.numberOfRolls + 1
            )
        }
    }
}