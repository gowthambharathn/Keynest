package skynetbee.gowtham.keynest.Screen.vaultunlock
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import skynetbee.gowtham.keynest.Security.SecurityManager

data class UnlockVaultUiState(
    val passwordInput: String = "",
    val isPasswordVisible: Boolean = false,
    val errorMessage: String? = null,
    val isUnlocked: Boolean = false
)

class UnlockVaultViewModel(application: Application) : AndroidViewModel(application) {

    private val securityManager = SecurityManager(application)
    private val _uiState = MutableStateFlow(UnlockVaultUiState())
    val uiState: StateFlow<UnlockVaultUiState> = _uiState.asStateFlow()

    fun onPasswordChanged(input: String) {
        _uiState.update { it.copy(passwordInput = input, errorMessage = null) }
    }

    fun togglePasswordVisibility() {
        _uiState.update { it.copy(isPasswordVisible = !it.isPasswordVisible) }
    }

    fun unlockVault() {
        val input = _uiState.value.passwordInput
        if (input.isBlank()) {
            _uiState.update { it.copy(errorMessage = "Enter your master password") }
            return
        }

        val isValid = securityManager.verifyMasterPassword(input)
        if (isValid) {
            _uiState.update { it.copy(isUnlocked = true) }
        } else {
            _uiState.update { it.copy(errorMessage = "Incorrect master password. Try again.") }
        }
    }
}