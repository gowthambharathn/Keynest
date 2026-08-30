package skynetbee.gowtham.keynest.ui.screen.setup


/**
 * Created by Gowtham Barath
 * Date: 23-06-2026
 */

import android.content.Context
import android.content.ContextWrapper
import android.util.Log
import android.widget.Toast
import androidx.biometric.BiometricPrompt
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import infinity.developers.coreutils.Ui.Nova.Components.Background.NovaWhiteBackground

private const val TAG = "SetupMasterPassword"

@Composable
fun SetupMasterPasswordScreen(
    viewModel: SetupMasterPasswordViewModel,
    onSetupComplete: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val activity = remember(context) { context.findFragmentActivity() }
    val uiState by viewModel.uiState.collectAsState()

    var masterPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var enableBiometric by remember { mutableStateOf(false) }

    fun triggerBiometricEnrollmentAndFinish() {
        val fragmentActivity = activity ?: run {
            Log.e(TAG, "FragmentActivity context missing")
            viewModel.completeSetup(masterPassword, confirmPassword, enableBiometrics = false)
            return
        }

        val executor = ContextCompat.getMainExecutor(fragmentActivity)
        val biometricPrompt = BiometricPrompt(
            fragmentActivity,
            executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    Log.d(TAG, "Fingerprint successfully verified during setup")
                    Toast.makeText(context, "Fingerprint saved successfully!", Toast.LENGTH_SHORT).show()
                    viewModel.completeSetup(masterPassword, confirmPassword, enableBiometrics = true)
                }

                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    Log.e(TAG, "Biometric enrollment error: $errString")
                    Toast.makeText(context, "Fingerprint verification failed. Setup continued with Master Password only.", Toast.LENGTH_LONG).show()
                    viewModel.completeSetup(masterPassword, confirmPassword, enableBiometrics = false)
                }
            }
        )

        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Confirm Fingerprint")
            .setSubtitle("Touch the sensor to verify and enable fingerprint login")
            .setNegativeButtonText("Cancel")
            .build()

        biometricPrompt.authenticate(promptInfo)
    }

    LaunchedEffect(uiState) {
        when (val state = uiState) {
            is SetupUiState.Success -> {
                onSetupComplete()
            }
            is SetupUiState.Error -> {
                Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
                viewModel.clearError()
            }
            else -> {}
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        NovaWhiteBackground()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Create Master Password",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF03A9F4)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "This password will be used to recover and unlock your vault.",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(32.dp))

            OutlinedTextField(
                value = masterPassword,
                onValueChange = { masterPassword = it },
                label = { Text("Master Password") },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Next
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = { Text("Confirm Master Password") },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (viewModel.isBiometricSensorAvailable()) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Checkbox(
                        checked = enableBiometric,
                        onCheckedChange = { enableBiometric = it }
                    )
                    Text(
                        text = "Enable Fingerprint Unlock",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
                Spacer(modifier = Modifier.height(24.dp))
            }

            Button(
                onClick = {
                    if (enableBiometric && viewModel.isBiometricSensorAvailable()) {
                        triggerBiometricEnrollmentAndFinish()
                    } else {
                        viewModel.completeSetup(masterPassword, confirmPassword, enableBiometrics = false)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Save & Continue")
            }
        }
    }
}

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