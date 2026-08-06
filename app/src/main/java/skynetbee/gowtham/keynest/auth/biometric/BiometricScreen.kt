package skynetbee.gowtham.keynest.auth.biometric

/**
 * Created by Gowtham Barath
 * Date: 23-06-2026
 */

import android.content.Context
import android.content.ContextWrapper
import android.util.Log
import androidx.biometric.BiometricPrompt
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.material.icons.filled.Key
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import infinity.developers.coreutils.Ui.Nova.Components.Background.NovaWhiteBackground
import skynetbee.gowtham.keynest.ui.screen.setup.AuthCard

private const val TAG = "BiometricScreen"

@Composable
fun BiometricScreen(
    viewModel: BiometricViewModel,
    onAuthenticated: () -> Unit,
    onFallbackToPassword: () -> Unit,
    modifier: Modifier = Modifier
) {
    Log.d(TAG, "Composable Started")

    val context = LocalContext.current
    val activity = remember(context) { context.findFragmentActivity() }
    val authState by viewModel.authState.collectAsState()

    fun showBiometricPrompt() {
        val fragmentActivity = activity ?: run {
            Log.e(TAG, "FragmentActivity not found in Context")
            return
        }

        if (!viewModel.isBiometricAvailable()) {
            Log.e(TAG, "Biometric hardware not available or enrolled")
            return
        }

        val executor = ContextCompat.getMainExecutor(fragmentActivity)

        val biometricPrompt = BiometricPrompt(
            fragmentActivity,
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
                    Log.w(TAG, "Authentication FAILED (Fingerprint not recognized)")
                }

                override fun onAuthenticationError(
                    errorCode: Int,
                    errString: CharSequence
                ) {
                    super.onAuthenticationError(errorCode, errString)
                    Log.e(TAG, "Authentication ERROR -> Code: $errorCode, Message: $errString")
                    viewModel.onAuthenticationError(errString.toString())
                }
            }
        )

        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Unlock KeyNest")
            .setSubtitle("Use your fingerprint to log in")
            .setNegativeButtonText("Use Password")
            .build()

        Log.d(TAG, "Showing biometric prompt")
        biometricPrompt.authenticate(promptInfo)
    }

    // React to Auth State updates
    LaunchedEffect(authState) {
        when (authState) {
            is BiometricAuthState.PromptReady -> {
                showBiometricPrompt()
            }
            is BiometricAuthState.Authenticated -> {
                Log.d(TAG, "Navigating to home/vault screen")
                onAuthenticated()
            }
            is BiometricAuthState.Unavailable -> {
                Log.w(TAG, "Biometrics unavailable. Redirecting to password.")
                onFallbackToPassword()
            }
            else -> {}
        }
    }

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        NovaWhiteBackground()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            // Header matching HomeScreen title styling
            Text(
                text = "KeyNest",
                style = MaterialTheme.typography.displayLarge,
                fontWeight = FontWeight.ExtraBold,
                color = Color(0xFF03A9F4)
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Subtitle matching HomeScreen subtitle styling
            Text(
                text = "Authenticate to unlock your vault",
                style = MaterialTheme.typography.titleMedium,
                color = Color(0xFF03A9F4).copy(alpha = 0.65f)
            )

            Spacer(modifier = Modifier.height(48.dp))

            // Action section using AuthCard components
            if (viewModel.isBiometricAvailable()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    AuthCard(
                        modifier = Modifier.weight(1f),
                        icon = Icons.Default.Fingerprint,
                        title = "Biometric",
                        onClick = { showBiometricPrompt() }
                    )

                    AuthCard(
                        modifier = Modifier.weight(1f),
                        icon = Icons.Default.Key,
                        title = "Password",
                        onClick = onFallbackToPassword
                    )
                }
            } else {
                // Single centered card if biometric sensor isn't available
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    AuthCard(
                        modifier = Modifier.weight(1f),
                        icon = Icons.Default.Key,
                        title = "Password",
                        onClick = onFallbackToPassword
                    )
                }
            }
        }
    }
}

/**
 * Safely traverses context hierarchy to find the enclosing [FragmentActivity].
 */
private fun Context.findFragmentActivity(): FragmentActivity? {
    var currentContext = this
    while (currentContext is ContextWrapper) {
        if (currentContext is FragmentActivity) {
            return currentContext
        }
        currentContext = currentContext.baseContext
    }
    return null
}