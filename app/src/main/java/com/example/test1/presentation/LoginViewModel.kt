package com.example.test1.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.test1.domain.repository.AuthRepository
import com.example.test1.domain.result.AuthResult
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class LoginState(
    val user: String = "",
    val pass: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
)

sealed interface LoginIntent {
    data class UserChanged(val value: String) : LoginIntent
    data class PassChanged(val value: String) : LoginIntent
    data object Submit : LoginIntent
}

sealed interface LoginEffect {
    data object NavigateHome : LoginEffect
    data class ShowMessage(val text: String) : LoginEffect
}

// -------------------- VIEWMODEL (evento one-shot) --------------------
//sealed interface LoginEvent {
//    data object GoHome : LoginEvent
//    data class ShowError(val message: String) : LoginEvent
//}

//data class DiceUiState(
//    val firstDieValue: Int? = null,
//    val secondDieValue: Int? = null,
//    val numberOfRolls: Int = 0,
//)
class LoginViewModel(private val repository: AuthRepository) : ViewModel() {

//    private val _uiState = MutableStateFlow(DiceUiState())
//    val uiState: StateFlow<DiceUiState> = _uiState.asStateFlow()

    private val _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> = _state.asStateFlow()

    private val _effects = MutableSharedFlow<LoginEffect>()
    val effects: SharedFlow<LoginEffect> = _effects.asSharedFlow()


    fun onIntent(intent: LoginIntent) {
        when (intent) {
            is LoginIntent.UserChanged ->
                _state.update { it.copy(user = intent.value, error = null) }

            is LoginIntent.PassChanged ->
                _state.update { it.copy(pass = intent.value, error = null) }

            LoginIntent.Submit -> submit()
        }
    }


    private fun submit() = viewModelScope.launch {
        val s = _state.value

        // Validación rápida (antes de pegarle al repo)
        if (s.user.isBlank() || s.pass.length < 4) {
            _state.update { it.copy(error = "Credenciales inválidas") }
            _effects.emit(LoginEffect.ShowMessage("Revisa usuario/contraseña"))
            return@launch
        }
        _state.update { it.copy(isLoading = true, error = null) }

        when (val result = repository.login(s.user.trim(), s.pass)) {
            AuthResult.Success -> {
                _state.update { it.copy(isLoading = false) }
                _effects.emit(LoginEffect.NavigateHome)
            }
            is AuthResult.Failure -> {
                _state.update { it.copy(isLoading = false, error = result.message) }
                _effects.emit(LoginEffect.ShowMessage(result.message))
            }
        }
    }
}