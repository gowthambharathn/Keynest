package skynetbee.gowtham.keynest.ui.screen.logincreatepassword

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import skynetbee.gowtham.keynest.domain.model.AuthMethod
import skynetbee.gowtham.keynest.domain.repository.AuthRepository
import java.security.MessageDigest
import javax.inject.Inject

/**
 * Created by Gowtham Barath
 * Date: 23-06-2026
 */

@HiltViewModel
class CreatePasswordViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _isAuthenticated = MutableStateFlow(false)
    val isAuthenticated: StateFlow<Boolean> = _isAuthenticated.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    fun createPassword(password: String) {
        if (password.isBlank()) {
            _errorMessage.value = "Password cannot be empty"
            return
        }

        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            try {
                // Secure SHA-256 password hashing
                val passwordHash = hashPassword(password)

                authRepository.savePasswordHash(passwordHash)
                authRepository.saveAuthMethod(AuthMethod.PASSWORD)

                _isAuthenticated.value = true
            } catch (e: Exception) {
                _errorMessage.value = e.message ?: "Failed to create password"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun clearErrorMessage() {
        _errorMessage.value = null
    }

    private fun hashPassword(password: String): String {
        val digest = MessageDigest.getInstance("SHA-256")
        val bytes = digest.digest(password.toByteArray(Charsets.UTF_8))
        return bytes.joinToString("") { "%02x".format(it) }
    }
}