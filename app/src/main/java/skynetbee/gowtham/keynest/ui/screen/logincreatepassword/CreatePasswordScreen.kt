package skynetbee.gowtham.keynest.ui.screen.logincreatepassword

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import infinity.developers.coreutils.Ui.Nova.Components.Background.NovaWhiteBackground
import infinity.developers.coreutils.Ui.Nova.Components.Button.NovaWhiteButton
import infinity.developers.coreutils.Ui.Nova.Components.TextField.NovaWhiteTextField

private const val TAG = "CreatePasswordScreen"

@Composable
fun CreatePasswordScreen(
    viewModel: CreatePasswordViewModel,
    onPasswordCreated: () -> Unit
) {
    Log.d(TAG, "CreatePasswordScreen Composed")

    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    var passwordVisible by remember { mutableStateOf(false) }
    var confirmVisible by remember { mutableStateOf(false) }

    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    // Observe StateFlows from ViewModel
    val isAuthenticated by viewModel.isAuthenticated.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    val passwordsMatch = password == confirmPassword && password.isNotBlank()
    val hasInputStarted = password.isNotEmpty() || confirmPassword.isNotEmpty()
    val showMismatchError = hasInputStarted && !passwordsMatch && confirmPassword.isNotEmpty()

    // Handle single-execution success navigation
    LaunchedEffect(isAuthenticated) {
        if (isAuthenticated) {
            Log.d(TAG, "Password creation confirmed. Navigating...")
            onPasswordCreated()
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
            // Top Branded Icon Surface
            Surface(
                shape = CircleShape,
                color = Color(0xFF03A9F4).copy(alpha = 0.12f),
                modifier = Modifier.size(90.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = null,
                        tint = Color(0xFF03A9F4),
                        modifier = Modifier.size(42.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            Text(
                text = "Create Master Password",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF03A9F4)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "This password protects all your saved credentials.\nMake sure you remember it carefully.",
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFF03A9F4).copy(alpha = 0.6f),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(40.dp))

            // Primary Password Field
            NovaWhiteTextField(
                value = password,
                onValueChange = {
                    password = it
                    if (errorMessage != null) viewModel.clearErrorMessage()
                },
                hint = "Master Password",
                modifier = Modifier.fillMaxWidth(),
                enabled = !isLoading,
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Next
                ),
                trailingIcon = {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            imageVector = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                            contentDescription = if (passwordVisible) "Hide password" else "Show password",
                            tint = Color(0xFF2196F3)
                        )
                    }
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Confirmation Password Field
            NovaWhiteTextField(
                value = confirmPassword,
                onValueChange = {
                    confirmPassword = it
                    if (errorMessage != null) viewModel.clearErrorMessage()
                },
                hint = "Confirm Password",
                modifier = Modifier.fillMaxWidth(),
                enabled = !isLoading,
                isError = showMismatchError || errorMessage != null,
                visualTransformation = if (confirmVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                        keyboardController?.hide()
                        if (passwordsMatch && !isLoading) {
                            viewModel.createPassword(password)
                        }
                    }
                ),
                trailingIcon = {
                    IconButton(onClick = { confirmVisible = !confirmVisible }) {
                        Icon(
                            imageVector = if (confirmVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                            contentDescription = if (confirmVisible) "Hide password" else "Show password",
                            tint = Color(0xFF2196F3)
                        )
                    }
                }
            )

            // Layout Jump Resistant Status / Error Bar
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, top = 6.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                when {
                    errorMessage != null -> {
                        Text(
                            text = errorMessage ?: "",
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                    passwordsMatch -> {
                        Text(
                            text = "✓ Passwords match",
                            color = Color(0xFF2E7D32),
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.Medium
                        )
                    }
                    showMismatchError -> {
                        Text(
                            text = "Passwords do not match",
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Unified Action Button
            NovaWhiteButton(
                text = "Create Password",
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .height(50.dp),
                enabled = passwordsMatch && !isLoading,
                loading = isLoading,
                onClick = {
                    focusManager.clearFocus()
                    keyboardController?.hide()
                    viewModel.createPassword(password)
                }
            )
        }
    }
}