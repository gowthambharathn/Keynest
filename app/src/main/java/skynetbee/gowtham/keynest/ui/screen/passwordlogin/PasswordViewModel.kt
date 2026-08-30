package skynetbee.gowtham.keynest.ui.screen.passwordlogin

/**
 * Created by Gowtham Barath
 * Date: 23-06-2026
 */

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface PasswordLoginUiState {
    object Idle : PasswordLoginUiState
    object Success : PasswordLoginUiState
    data class Error(val message: String) : PasswordLoginUiState
}

@HiltViewModel
class PasswordLoginViewModel @Inject constructor(
    // Inject your vault/encryption repository here to verify password against saved salt/hash
) : ViewModel() {

    private val _uiState = MutableStateFlow<PasswordLoginUiState>(PasswordLoginUiState.Idle)
    val uiState: StateFlow<PasswordLoginUiState> = _uiState.asStateFlow()

    fun verifyMasterPassword(password: String) {
        if (password.isBlank()) {
            _uiState.value = PasswordLoginUiState.Error("Please enter your Master Password")
            return
        }

        viewModelScope.launch {
            // TODO: Replace with your actual password verification / database unlock logic
            val isValid = true // Replace with repository verification logic

            if (isValid) {
                _uiState.value = PasswordLoginUiState.Success
            } else {
                _uiState.value = PasswordLoginUiState.Error("Incorrect Master Password")
            }
        }
    }

    fun clearError() {
        _uiState.value = PasswordLoginUiState.Idle
    }
}