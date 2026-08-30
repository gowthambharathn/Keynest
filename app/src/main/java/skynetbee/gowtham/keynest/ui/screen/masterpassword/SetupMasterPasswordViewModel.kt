package skynetbee.gowtham.keynest.ui.screen.masterpassword


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
import skynetbee.gowtham.keynest.data.preference.AuthPreferences
import skynetbee.gowtham.keynest.data.security.BiometricHelper
import javax.inject.Inject

sealed interface SetupUiState {
    object Idle : SetupUiState
    object BiometricVerificationRequired : SetupUiState
    object Success : SetupUiState
    data class Error(val message: String) : SetupUiState
}

@HiltViewModel
class SetupMasterPasswordViewModel @Inject constructor(
    private val authPreferences: AuthPreferences,
    private val biometricHelper: BiometricHelper
) : ViewModel() {

    private val _uiState = MutableStateFlow<SetupUiState>(SetupUiState.Idle)
    val uiState: StateFlow<SetupUiState> = _uiState.asStateFlow()

    var masterPassword = MutableStateFlow("")
    var confirmPassword = MutableStateFlow("")
    var isBiometricEnabled = MutableStateFlow(false)

    fun isBiometricHardwareAvailable(): Boolean {
        return biometricHelper.isBiometricAvailable()
    }

    fun onBiometricToggleChanged(enabled: Boolean) {
        if (enabled && !isBiometricHardwareAvailable()) {
            _uiState.value = SetupUiState.Error("Biometric hardware is not available on this device")
            isBiometricEnabled.value = false
            return
        }
        isBiometricEnabled.value = enabled
    }

    fun onSubmitClicked() {
        val password = masterPassword.value.trim()
        val confirm = confirmPassword.value.trim()

        if (password.isEmpty()) {
            _uiState.value = SetupUiState.Error("Master Password cannot be empty")
            return
        }

        if (password.length < 6) {
            _uiState.value = SetupUiState.Error("Password must be at least 6 characters")
            return
        }

        if (password != confirm) {
            _uiState.value = SetupUiState.Error("Passwords do not match")
            return
        }

        if (isBiometricEnabled.value) {
            _uiState.value = SetupUiState.BiometricVerificationRequired
        } else {
            saveMasterPassword(biometricEnabled = false)
        }
    }

    fun onBiometricVerifiedAndSave() {
        saveMasterPassword(biometricEnabled = true)
    }

    private fun saveMasterPassword(biometricEnabled: Boolean) {
        viewModelScope.launch {
            try {
                authPreferences.setMasterPasswordCreated(true)
                authPreferences.setBiometricEnabled(biometricEnabled)
                _uiState.value = SetupUiState.Success
            } catch (e: Exception) {
                _uiState.value = SetupUiState.Error("Failed to save credentials: ${e.localizedMessage}")
            }
        }
    }

    fun resetState() {
        _uiState.value = SetupUiState.Idle
    }
}