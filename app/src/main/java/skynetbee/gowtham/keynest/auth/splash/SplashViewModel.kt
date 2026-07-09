package skynetbee.gowtham.keynest.auth.splash

/**
 * Created by Gowtham Barath
 * Date: 23-06-2026
 */

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import skynetbee.gowtham.keynest.domain.model.AuthMethod
import skynetbee.gowtham.keynest.domain.usecase.GetAuthMethodUseCase
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getAuthMethodUseCase: GetAuthMethodUseCase
) : ViewModel() {

    companion object {
        private const val TAG = "SplashViewModel"
    }

    private val _authMethod =
        MutableStateFlow(AuthMethod.NONE)

    val authMethod: StateFlow<AuthMethod> =
        _authMethod.asStateFlow()

    init {
        Log.d(TAG, "ViewModel initialized")
        loadAuthMethod()
    }

    private fun loadAuthMethod() {
        Log.d(TAG, "loadAuthMethod() called")

        viewModelScope.launch {
            try {
                Log.d(TAG, "Starting auth method collection")

                getAuthMethodUseCase().collect { method ->

                    Log.d(
                        TAG,
                        "Received AuthMethod from UseCase: $method"
                    )

                    _authMethod.value = method

                    Log.d(
                        TAG,
                        "StateFlow updated: ${_authMethod.value}"
                    )
                }

            } catch (e: Exception) {

                Log.e(
                    TAG,
                    "Error while loading auth method",
                    e
                )
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "ViewModel cleared")
    }
}