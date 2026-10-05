package skynetbee.gowtham.keynest.Navigation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import skynetbee.gowtham.keynest.Security.SecurityManager
import skynetbee.gowtham.keynest.navigation.Screen

sealed interface EngineStarterState {
    object Loading : EngineStarterState
    data class Success(val startDestination: String) : EngineStarterState
}

class EngineStarterViewModel(application: Application) : AndroidViewModel(application) {

    private val securityManager = SecurityManager(application)

    private val _engineState = MutableStateFlow<EngineStarterState>(EngineStarterState.Loading)
    val engineState: StateFlow<EngineStarterState> = _engineState.asStateFlow()

    init {
        checkAppLaunchDestination()
    }

    private fun checkAppLaunchDestination() {
        val isVaultCreated = securityManager.isMasterPasswordSet()
        if (isVaultCreated) {
            // Master password already set -> Show Unlock Screen
            _engineState.value = EngineStarterState.Success(Screen.UnlockVault.route)
        } else {
            // First time opening app -> Show Create Master Password Screen
            _engineState.value = EngineStarterState.Success(Screen.SetupMasterPassword.route)
        }
    }
}