package skynetbee.gowtham.keynest.ui.screen.setup


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

    fun isBiometricSensorAvailable(): Boolean {
        return biometricHelper.isBiometricAvailable()
    }

    fun completeSetup(masterPassword: String, confirmPassword: String, enableBiometrics: Boolean) {
        if (masterPassword.isEmpty() || confirmPassword.isEmpty()) {
            _uiState.value = SetupUiState.Error("Please enter and confirm your password")
            return
        }

        if (masterPassword.length < 6) {
            _uiState.value = SetupUiState.Error("Master Password must be at least 6 characters")
            return
        }

        if (masterPassword != confirmPassword) {
            _uiState.value = SetupUiState.Error("Passwords do not match")
            return
        }

        viewModelScope.launch {
            // Save preferences
            authPreferences.setMasterPasswordCreated(true)
            authPreferences.setBiometricEnabled(enableBiometrics)
            _uiState.value = SetupUiState.Success
        }
    }

    fun clearError() {
        _uiState.value = SetupUiState.Idle
    }
}