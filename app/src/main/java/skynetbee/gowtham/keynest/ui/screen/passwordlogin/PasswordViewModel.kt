package skynetbee.gowtham.keynest.ui.screen.passwordlogin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import skynetbee.gowtham.keynest.data.security.PasswordHasher
import skynetbee.gowtham.keynest.domain.usecase.CreatePasswordUseCase
import skynetbee.gowtham.keynest.domain.usecase.VerifyPasswordUseCase
import javax.inject.Inject

@HiltViewModel
class PasswordViewModel @Inject constructor(
    private val createPasswordUseCase: CreatePasswordUseCase,
    private val verifyPasswordUseCase: VerifyPasswordUseCase,
    private val passwordHasher: PasswordHasher
) : ViewModel() {

    private val _isAuthenticated = MutableStateFlow(false)
    val isAuthenticated: StateFlow<Boolean> = _isAuthenticated.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    // NEW: Expose loading state to manage UI progress bars and disable buttons during execution
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    fun createPassword(password: String) {
        if (password.isBlank()) return

        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                val hash = passwordHasher.hash(password)
                createPasswordUseCase(hash)
            } catch (e: Exception) {
                _errorMessage.value = "Failed to create password"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun verifyPassword(password: String) {
        if (password.isBlank()) return

        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                // NOTE: If verifyPasswordUseCase compares hashes instead of raw text,
                // you may want to change this to: verifyPasswordUseCase(passwordHasher.hash(password))
                val success = verifyPasswordUseCase(password)

                if (success) {
                    _isAuthenticated.value = true
                } else {
                    _isAuthenticated.value = false
                    _errorMessage.value = "Incorrect password"
                }
            } catch (e: Exception) {
                _isAuthenticated.value = false
                _errorMessage.value = "Authentication failed"
            } finally {
                _isLoading.value = false
            }
        }
    }

    // RENAMED: Changed from clearError to clearErrorMessage to line up exactly with the UI requirements
    fun clearErrorMessage() {
        _errorMessage.value = null
    }

    fun logout() {
        _isAuthenticated.value = false
        _isLoading.value = false
        _errorMessage.value = null
    }
}