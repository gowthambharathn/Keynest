package skynetbee.gowtham.keynest.auth.biometric


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

@HiltViewModel
class BiometricViewModel @Inject constructor(
    private val biometricHelper: BiometricHelper
) : ViewModel() {

    private val _isAuthenticated =
        MutableStateFlow(false)

    val isAuthenticated: StateFlow<Boolean> =
        _isAuthenticated.asStateFlow()

    fun isBiometricAvailable(): Boolean {
        return biometricHelper.isBiometricAvailable()
    }

    fun onAuthenticationSuccess() {
        _isAuthenticated.value = true
    }
}