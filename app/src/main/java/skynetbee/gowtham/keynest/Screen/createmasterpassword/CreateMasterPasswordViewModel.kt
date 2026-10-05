package skynetbee.gowtham.keynest.Screen

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import skynetbee.gowtham.keynest.Security.SecurityManager

enum class MasterPasswordStrength(val label: String, val score: Float) {
    WEAK("Weak", 0.25f),
    MEDIUM("Medium", 0.50f),
    STRONG("Strong", 0.75f),
    VERY_STRONG("Very Strong", 1.0f)
}

data class CreateMasterPasswordUiState(
    val masterPassword: String = "",
    val confirmPassword: String = "",
    val isPasswordVisible: Boolean = false,
    val isConfirmPasswordVisible: Boolean = false,
    val isBiometricEnabled: Boolean = false,
    val strength: MasterPasswordStrength = MasterPasswordStrength.WEAK,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isSetupComplete: Boolean = false
)

class CreateMasterPasswordViewModel(application: Application) : AndroidViewModel(application) {

    private val securityManager = SecurityManager(application)

    private val _uiState = MutableStateFlow(CreateMasterPasswordUiState())
    val uiState: StateFlow<CreateMasterPasswordUiState> = _uiState.asStateFlow()

    fun onMasterPasswordChanged(password: String) {
        val strength = calculateStrength(password)
        _uiState.update {
            it.copy(
                masterPassword = password,
                strength = strength,
                errorMessage = null
            )
        }
    }

    fun onConfirmPasswordChanged(password: String) {
        _uiState.update {
            it.copy(
                confirmPassword = password,
                errorMessage = null
            )
        }
    }

    fun togglePasswordVisibility() {
        _uiState.update { it.copy(isPasswordVisible = !it.isPasswordVisible) }
    }

    fun toggleConfirmPasswordVisibility() {
        _uiState.update { it.copy(isConfirmPasswordVisible = !it.isConfirmPasswordVisible) }
    }

    fun onBiometricToggleChanged(index: Int) {
        // Index 0: Off, Index 1: On
        _uiState.update { it.copy(isBiometricEnabled = index == 1) }
    }

    fun saveMasterPassword() {
        val state = _uiState.value

        when {
            state.masterPassword.isBlank() -> {
                _uiState.update { it.copy(errorMessage = "Master password cannot be empty.") }
                return
            }
            state.masterPassword.length < 8 -> {
                _uiState.update { it.copy(errorMessage = "Master password must be at least 8 characters.") }
                return
            }
            state.masterPassword != state.confirmPassword -> {
                _uiState.update { it.copy(errorMessage = "Passwords do not match.") }
                return
            }
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            try {
                // Save hashed password securely to EncryptedSharedPreferences
                securityManager.saveMasterPassword(state.masterPassword)

                // Brief delay for visual loading state transition
                delay(500)

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        isSetupComplete = true
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = "Failed to create vault: ${e.localizedMessage}"
                    )
                }
            }
        }
    }

    private fun calculateStrength(password: String): MasterPasswordStrength {
        if (password.length < 6) return MasterPasswordStrength.WEAK
        var score = 0
        if (password.length >= 10) score += 1
        if (password.length >= 14) score += 1
        if (password.any { it.isDigit() }) score += 1
        if (password.any { it.isUpperCase() }) score += 1
        if (password.any { !it.isLetterOrDigit() }) score += 1

        return when {
            score <= 1 -> MasterPasswordStrength.WEAK
            score in 2..3 -> MasterPasswordStrength.MEDIUM
            score == 4 -> MasterPasswordStrength.STRONG
            else -> MasterPasswordStrength.VERY_STRONG
        }
    }
}