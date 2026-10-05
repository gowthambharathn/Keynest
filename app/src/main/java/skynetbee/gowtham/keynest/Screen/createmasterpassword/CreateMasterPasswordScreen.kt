package skynetbee.gowtham.keynest.Screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

import infinity.developers.coreutils.Ui.Nova.Components.GlowPosition
import infinity.developers.coreutils.Ui.Nova.Components.NovaBackground
import infinity.developers.coreutils.Ui.Nova.Components.NovaButton
import infinity.developers.coreutils.Ui.Nova.Components.NovaCard
import infinity.developers.coreutils.Ui.Nova.Components.NovaTextField
import skynetbee.gowtham.keynest.Utils.NovaLoadingIndicator

// Test Accent Color
private val TestAccentColor = Color(0xFF2196F3)

@Composable
fun CreateMasterPasswordScreen(
    viewModel: CreateMasterPasswordViewModel = viewModel(),
    onSetupComplete: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.isSetupComplete) {
        if (uiState.isSetupComplete) {
            onSetupComplete()
        }
    }

    NovaBackground(position = GlowPosition.CENTER) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Nova Shield Badge with Test Color Glow
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                        .background(
                            Brush.radialGradient(
                                colors = listOf(
                                    TestAccentColor.copy(alpha = 0.35f),
                                    Color.Transparent
                                )
                            )
                        )
                        .border(
                            width = 1.5.dp,
                            brush = Brush.linearGradient(
                                colors = listOf(
                                    TestAccentColor.copy(alpha = 0.8f),
                                    TestAccentColor.copy(alpha = 0.2f)
                                )
                            ),
                            shape = CircleShape
                        )
                ) {
                    Icon(
                        imageVector = Icons.Default.Shield,
                        contentDescription = "KeyNest Shield",
                        tint = TestAccentColor,
                        modifier = Modifier.size(62.dp)
                    )
                }
                Spacer(modifier = Modifier.height(24.dp))

                // Centered Titles
                Text(
                    text = "Initialize Vault",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontSize = 28.sp,
                        letterSpacing = 0.5.sp
                    ),
                    fontWeight = FontWeight.Bold,
                    color = TestAccentColor,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Create a master password to derive your SQLCipher encryption key. This password cannot be reset or recovered.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f),
                    textAlign = TextAlign.Center,
                    lineHeight = 20.sp,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )

                Spacer(modifier = Modifier.height(28.dp))

                // Error Message Card
                AnimatedVisibility(
                    visible = uiState.errorMessage != null,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    NovaCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.15f))
                                .padding(14.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Info,
                                contentDescription = "Error",
                                tint = MaterialTheme.colorScheme.error,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = uiState.errorMessage ?: "",
                                color = MaterialTheme.colorScheme.error,
                                style = MaterialTheme.typography.bodySmall,
                                fontWeight = FontWeight.Medium,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }

                // Professional Inline Password Strength Label (Above Password Field)
                AnimatedVisibility(
                    visible = uiState.masterPassword.isNotEmpty(),
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    val (strengthColor, strengthLabel) = when (uiState.strength) {
                        MasterPasswordStrength.WEAK -> MaterialTheme.colorScheme.error to "Weak password"
                        MasterPasswordStrength.MEDIUM -> Color(0xFFFFB300) to "Medium strength"
                        MasterPasswordStrength.STRONG -> TestAccentColor to "Strong password"
                        MasterPasswordStrength.VERY_STRONG -> Color(0xFF00E676) to "Very strong password"
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 4.dp, vertical = 6.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = strengthLabel,
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = strengthColor
                            )
                        }
                    }
                }

                // Master Password Input Field
                NovaTextField(
                    value = uiState.masterPassword,
                    onValueChange = { viewModel.onMasterPasswordChanged(it) },
                    hint = "Master Password",
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = null,
                            tint = TestAccentColor
                        )
                    },
                    trailingIcon = {
                        IconButton(onClick = { viewModel.togglePasswordVisibility() }) {
                            Icon(
                                imageVector = if (uiState.isPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                contentDescription = "Toggle Visibility",
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    },
                    visualTransformation = if (uiState.isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Next
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Confirm Password Field
                NovaTextField(
                    value = uiState.confirmPassword,
                    onValueChange = { viewModel.onConfirmPasswordChanged(it) },
                    hint = "Confirm Master Password",
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = null,
                            tint = TestAccentColor
                        )
                    },
                    trailingIcon = {
                        IconButton(onClick = { viewModel.toggleConfirmPasswordVisibility() }) {
                            Icon(
                                imageVector = if (uiState.isConfirmPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                contentDescription = "Toggle Visibility",
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    },
                    visualTransformation = if (uiState.isConfirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(28.dp))

                // Action Button or Loading Indicator
                if (uiState.isLoading) {
                    NovaLoadingIndicator(
                        size = 48.dp,
                        strokeWidth = 4.dp,
                        primaryColor = TestAccentColor,
                        secondaryColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                } else {
                    NovaButton(
                        text = "Create Encrypted Vault",
                        onClick = { viewModel.saveMasterPassword() },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                Spacer(modifier = Modifier.height(28.dp))
            }
        }
    }
}