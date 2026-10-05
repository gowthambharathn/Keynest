package skynetbee.gowtham.keynest.Screen.addpassword

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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Title
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

import infinity.developers.coreutils.Ui.Nova.Components.GlowPosition
import infinity.developers.coreutils.Ui.Nova.Components.NovaBackground
import infinity.developers.coreutils.Ui.Nova.Components.NovaButton
import infinity.developers.coreutils.Ui.Nova.Components.NovaCard
import infinity.developers.coreutils.Ui.Nova.Components.NovaTextField
import infinity.developers.coreutils.Ui.Nova.Components.NovaToggle
import skynetbee.gowtham.keynest.Screen.MasterPasswordStrength
import skynetbee.gowtham.keynest.Utils.NovaLoadingIndicator

private val TestAccentColor = Color(0xFF2196F3)

@Composable
fun AddPasswordScreen(
    viewModel: AddPasswordViewModel = viewModel(),
    onNavigateBack: () -> Unit,
    onSaveSuccess: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.isSavedSuccess) {
        if (uiState.isSavedSuccess) {
            onSaveSuccess()
        }
    }

    NovaBackground(position = GlowPosition.TOP_RIGHT) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                Spacer(modifier = Modifier.height(20.dp))

                // App Bar Navigation Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "New Password Record",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Center Icon Glow Badge
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(120.dp)
                        .align(Alignment.CenterHorizontally)
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
                            width = 1.dp,
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
                        imageVector = Icons.Default.Key,
                        contentDescription = "Add Password",
                        tint = TestAccentColor,
                        modifier = Modifier.size(62.dp)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Error Notification
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
                                .padding(12.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Info,
                                contentDescription = "Error",
                                tint = MaterialTheme.colorScheme.error,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = uiState.errorMessage ?: "",
                                color = MaterialTheme.colorScheme.error,
                                style = MaterialTheme.typography.bodySmall,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }

                // Service / Title Field
                NovaTextField(
                    value = uiState.title,
                    onValueChange = { viewModel.onTitleChanged(it) },
                    hint = "Title (e.g., Google, GitHub)",
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Title,
                            contentDescription = null,
                            tint = TestAccentColor
                        )
                    },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(14.dp))

                // Password Input Field
                NovaTextField(
                    value = uiState.password,
                    onValueChange = { viewModel.onPasswordChanged(it) },
                    hint = "Password",
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
                        imeAction = ImeAction.Done
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Password Strength Indicator (Inline)
                AnimatedVisibility(
                    visible = uiState.password.isNotEmpty(),
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
                            .padding(horizontal = 4.dp, vertical = 4.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Password Strength",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                text = strengthLabel,
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = strengthColor
                            )
                        }

                        Spacer(modifier = Modifier.height(4.dp))

                        LinearProgressIndicator(
                            progress = { uiState.strength.score },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(3.dp)
                                .clip(RoundedCornerShape(1.5.dp)),
                            color = strengthColor,
                            trackColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // ================= Password Generator (NEW) =================

                // 1) Generator strength selector + Generate button
                Text(
                    text = "Generate Password",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(start = 4.dp, bottom = 6.dp)
                )
                NovaToggle(
                    options = viewModel.generationStrengths,
                    selectedIndex = uiState.selectedStrengthIndex,
                    onToggle = { viewModel.onGenerationStrengthSelected(it) }
                )

                Spacer(modifier = Modifier.height(12.dp))

                NovaButton(
                    text = "Generate Password",
                    onClick = { viewModel.generatePassword() },
                    modifier = Modifier.fillMaxWidth()
                )

                // 2) Generated password result card with regenerate icon
                AnimatedVisibility(
                    visible = uiState.showGeneratedPasswordCard,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    NovaCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 14.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(14.dp)
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "Suggested Password",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = uiState.generatedPassword,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = TestAccentColor
                                )
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            IconButton(onClick = { viewModel.regeneratePassword() }) {
                                Icon(
                                    imageVector = Icons.Default.Refresh,
                                    contentDescription = "Regenerate Password",
                                    tint = TestAccentColor
                                )
                            }
                        }
                    }
                }

                // ================= End Password Generator =================

                Spacer(modifier = Modifier.height(28.dp))

                // Save Action Button
                if (uiState.isLoading) {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        NovaLoadingIndicator(
                            size = 44.dp,
                            strokeWidth = 4.dp,
                            primaryColor = TestAccentColor,
                            secondaryColor = MaterialTheme.colorScheme.surfaceVariant
                        )
                    }
                } else {
                    NovaButton(
                        text = "Encrypt & Save to Vault",
                        onClick = { viewModel.savePassword() },
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}