package skynetbee.gowtham.keynest.ui.screen.splash

/**
 * Created by Gowtham Barath
 * Date: 23-06-2026
 */

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import skynetbee.gowtham.keynest.data.preference.AuthPreferences
import javax.inject.Inject

sealed interface SplashDestination {
    object Loading : SplashDestination
    object MasterPasswordSetup : SplashDestination
    object BiometricLogin : SplashDestination
    object PasswordLogin : SplashDestination
}

@HiltViewModel
class SplashViewModel @Inject constructor(
    authPreferences: AuthPreferences
) : ViewModel() {

    val destination: StateFlow<SplashDestination> = combine(
        authPreferences.isMasterPasswordSet,
        authPreferences.isBiometricEnabled
    ) { isPasswordSet, isBiometricEnabled ->
        when {
            !isPasswordSet -> SplashDestination.MasterPasswordSetup
            isBiometricEnabled -> SplashDestination.BiometricLogin
            else -> SplashDestination.PasswordLogin
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = SplashDestination.Loading
    )
}