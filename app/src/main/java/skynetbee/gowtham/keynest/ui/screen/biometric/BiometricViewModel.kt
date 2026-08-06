package skynetbee.gowtham.keynest.ui.screen.biometric

/**
 * Created by Gowtham Barath
 * Date: 23-06-2026
 */

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import skynetbee.gowtham.keynest.data.security.BiometricHelper
import javax.inject.Inject

sealed interface BiometricAuthState {
    object Idle : BiometricAuthState
    object PromptReady : BiometricAuthState
    object Authenticated : BiometricAuthState
    object Unavailable : BiometricAuthState
    data class Error(val message: String) : BiometricAuthState
}

@HiltViewModel
class BiometricViewModel @Inject constructor(
    private val biometricHelper: BiometricHelper
) : ViewModel() {

    private val _authState = MutableStateFlow<BiometricAuthState>(BiometricAuthState.Idle)
    val authState: StateFlow<BiometricAuthState> = _authState.asStateFlow()

    init {
        checkBiometricAvailability()
    }

    fun checkBiometricAvailability() {
        if (biometricHelper.isBiometricAvailable()) {
            _authState.value = BiometricAuthState.PromptReady
        } else {
            _authState.value = BiometricAuthState.Unavailable
        }
    }

    fun isBiometricAvailable(): Boolean {
        return biometricHelper.isBiometricAvailable()
    }

    fun onAuthenticationSuccess() {
        _authState.value = BiometricAuthState.Authenticated
    }

    fun onAuthenticationError(errorMessage: String) {
        _authState.value = BiometricAuthState.Error(errorMessage)
    }

    fun resetState() {
        if (isBiometricAvailable()) {
            _authState.value = BiometricAuthState.PromptReady
        }
    }
}