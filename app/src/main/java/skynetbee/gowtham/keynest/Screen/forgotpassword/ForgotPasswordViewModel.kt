package skynetbee.gowtham.keynest.Screen.forgotpassword

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import skynetbee.gowtham.keynest.Security.SecurityManager

data class ForgotPasswordUiState(
    val showConfirmationDialog: Boolean = false,
    val isWiping: Boolean = false,
    val isDataWiped: Boolean = false
)

class ForgotPasswordViewModel(application: Application) : AndroidViewModel(application) {

    private val securityManager = SecurityManager(application)

    private val _uiState = MutableStateFlow(ForgotPasswordUiState())
    val uiState: StateFlow<ForgotPasswordUiState> = _uiState.asStateFlow()

    fun onWipeDataClicked() {
        _uiState.update { it.copy(showConfirmationDialog = true) }
    }

    fun dismissConfirmationDialog() {
        _uiState.update { it.copy(showConfirmationDialog = false) }
    }

    fun confirmWipeData() {
        viewModelScope.launch {
            _uiState.update { it.copy(showConfirmationDialog = false, isWiping = true) }

            // Wipe KeyStore keys, encrypted shared preferences, and database tables
            securityManager.clearAllData()

            _uiState.update { it.copy(isWiping = false, isDataWiped = true) }
        }
    }
}