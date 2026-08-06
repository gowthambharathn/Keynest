package skynetbee.gowtham.keynest.ui.screen.passwordlogin

/**
 * Created by Gowtham Barath
 * Date: 23-06-2026
 */

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import infinity.developers.coreutils.Ui.Nova.Components.Background.NovaWhiteBackground
import infinity.developers.coreutils.Ui.Nova.Components.Button.NovaWhiteButton
import infinity.developers.coreutils.Ui.Nova.Components.TextField.NovaWhiteTextField

private const val TAG = "PasswordLoginScreen"

@Composable
fun PasswordLoginScreen(
    viewModel: PasswordViewModel,
    onLoginSuccess: () -> Unit
) {
    Log.d(TAG, "PasswordLoginScreen Composed")

    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    val focusManager = LocalFocusManager.current

    val isAuthenticated by viewModel.isAuthenticated.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState() // Make sure to add this state to your ViewModel

    LaunchedEffect(isAuthenticated) {
        if (isAuthenticated) {
            Log.d(TAG, "Login successful. Navigating to next screen")
            onLoginSuccess()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        NovaWhiteBackground()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "KeyNest",
                style = MaterialTheme.typography.displayLarge,
                fontWeight = FontWeight.ExtraBold,
                color = Color(0xFF03A9F4)
            )
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Unlock your password vault",
                style = MaterialTheme.typography.titleMedium,
                color = Color(0xFF03A9F4).copy(alpha = 0.6f)
            )

            Spacer(modifier = Modifier.height(40.dp))

            NovaWhiteTextField(
                value = password,
                onValueChange = {
                    password = it

                    // Clear error while typing
                    if (errorMessage != null) {
                        viewModel.clearErrorMessage()
                    }
                },
                hint = "Master Password",
                modifier = Modifier.fillMaxWidth(),

                enabled = !isLoading,

                isError = errorMessage != null,

                visualTransformation = if (passwordVisible)
                    VisualTransformation.None
                else
                    PasswordVisualTransformation(),

                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),

                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()

                        if (password.isNotBlank() && !isLoading) {
                            viewModel.verifyPassword(password)
                        }
                    }
                ),

                trailingIcon = {
                    val image = if (passwordVisible)
                        Icons.Default.Visibility
                    else
                        Icons.Default.VisibilityOff

                    val description = if (passwordVisible)
                        "Hide password"
                    else
                        "Show password"

                    IconButton(
                        onClick = {
                            passwordVisible = !passwordVisible
                        }
                    ) {
                        Icon(
                            imageVector = image,
                            contentDescription = description,
                            tint = Color(0xFF2196F3)
                        )
                    }
                }
            )

            // Dynamic supporting text placeholder fixes layout jumps
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, top = 4.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                if (errorMessage != null) {
                    Text(
                        text = errorMessage ?: "",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            NovaWhiteButton(
                text = "Unlock Vault",

                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .height(50.dp),

                enabled = password.isNotBlank() && !isLoading,

                loading = isLoading,

                onClick = {
                    focusManager.clearFocus()
                    viewModel.verifyPassword(password)
                }
            )
            Spacer(modifier = Modifier.height(16.dp))

            TextButton(
                onClick = { Log.d(TAG, "Forgot Password execution flow.") }
            ) {
                Text(
                    text = "Forgot Password?",
                    color = Color(0xFF03A9F4).copy(alpha = 0.8f)
                )
            }
        }
    }
}