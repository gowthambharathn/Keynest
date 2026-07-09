package skynetbee.gowtham.keynest.auth.biometric

/**
 * Created by Gowtham Barath
 * Date: 23-06-2026
 */

import android.util.Log
import androidx.biometric.BiometricPrompt
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity

private const val TAG = "BiometricScreen"

@Composable
fun BiometricScreen(
    viewModel: BiometricViewModel,
    onAuthenticated: () -> Unit
) {

    Log.d(TAG, "Composable Started")

    val context = LocalContext.current
    val activity = context as FragmentActivity

    val isAuthenticated by
    viewModel.isAuthenticated.collectAsState()

    Log.d(TAG, "Current Authentication State: $isAuthenticated")

    LaunchedEffect(Unit) {

        Log.d(TAG, "Launching biometric authentication")

        if (!viewModel.isBiometricAvailable()) {
            Log.e(TAG, "Biometric not available on this device")
            return@LaunchedEffect
        }

        Log.d(TAG, "Biometric available")

        val executor =
            ContextCompat.getMainExecutor(activity)

        val biometricPrompt =
            BiometricPrompt(
                activity,
                executor,
                object : BiometricPrompt.AuthenticationCallback() {

                    override fun onAuthenticationSucceeded(
                        result: BiometricPrompt.AuthenticationResult
                    ) {
                        super.onAuthenticationSucceeded(result)

                        Log.d(TAG, "Authentication SUCCESS")

                        viewModel.onAuthenticationSuccess()
                    }

                    override fun onAuthenticationFailed() {
                        super.onAuthenticationFailed()

                        Log.w(TAG, "Authentication FAILED")
                    }

                    override fun onAuthenticationError(
                        errorCode: Int,
                        errString: CharSequence
                    ) {
                        super.onAuthenticationError(errorCode, errString)

                        Log.e(
                            TAG,
                            "Authentication ERROR -> Code: $errorCode, Message: $errString"
                        )
                    }
                }
            )

        val promptInfo =
            BiometricPrompt.PromptInfo.Builder()
                .setTitle("Unlock KeyNest")
                .setSubtitle("Authenticate to continue")
                .setNegativeButtonText("Cancel")
                .build()

        Log.d(TAG, "Showing biometric prompt")

        biometricPrompt.authenticate(promptInfo)
    }

    LaunchedEffect(isAuthenticated) {

        Log.d(TAG, "Authentication State Changed: $isAuthenticated")

        if (isAuthenticated) {
            Log.d(TAG, "Navigating to next screen")
            onAuthenticated()
        }
    }
}