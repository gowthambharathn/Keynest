package skynetbee.gowtham.keynest.auth.createpassword

/**
 * Created by Gowtham Barath
 * Date: 05-07-2026
 * Enhanced UI Implementation
 */

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import infinity.developers.coreutils.Ui.Nova.Components.Background.NovaWhiteBackground
import infinity.developers.coreutils.Ui.Nova.Components.Button.NovaWhiteButton
import infinity.developers.coreutils.Ui.Nova.Components.Card.NovaWhiteCard
import infinity.developers.coreutils.Ui.Nova.Components.Slider.NovaWhiteSlider
import infinity.developers.coreutils.Ui.Nova.Components.TextField.NovaWhiteTextField
import infinity.developers.coreutils.Ui.Nova.Components.Toggle.NovaWhiteToggle


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreatePasswordScreen(
    title: String,
    password: String,
    length: Float,
    selectedDifficulty: Difficulty,
    onTitleChange: (String) -> Unit,
    onDifficultyChange: (Difficulty) -> Unit,
    onLengthChange: (Float) -> Unit,
    onGenerateClick: () -> Unit,
    onCopyClick: () -> Unit,
    onSaveClick: () -> Unit
) {
    val brandColor = Color(0xFF2196F3)
    val neutralTextAlpha = brandColor.copy(alpha = 0.6f)

    Box(modifier = Modifier.fillMaxSize()) {
        NovaWhiteBackground()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 12.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // Screen Header Section
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Generate Password",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFFFFFFF),
                    textAlign = TextAlign.Center
                )
            }

            // Input: Context / Label Field
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = "What is this password for?",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = brandColor
                )
                NovaWhiteTextField(
                    value = title,
                    onValueChange = onTitleChange,
                    hint = "e.g., Google, Work Email",
                    modifier = Modifier.fillMaxWidth().height(50.dp)
                )
            }

            // Input: Difficulty Chips Selection
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text(
                    text = "Security Strength",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = brandColor
                )
                NovaWhiteToggle(
                    options = listOf("Easy", "Medium", "Hard"),
                    selectedIndex = selectedDifficulty.ordinal,
                    onToggle = { index ->
                        onDifficultyChange(Difficulty.entries[index])
                    }
                )
            }

            // Input: Length Slider Config
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Password Length",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = brandColor
                    )
                    Text(
                        text = "${length.toInt()} Characters",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        color = brandColor
                    )
                }
                NovaWhiteCard(
                    modifier = Modifier.fillMaxWidth()
                ) {

                    NovaWhiteSlider(
                        value = length,
                        onValueChange = onLengthChange,
                        valueRange = 6f..64f
                    )

                }
            }

            // Primary Generation Action
            NovaWhiteButton(
                onClick = onGenerateClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(14.dp),
                text = "Generate Secure Password",
            )

            // Layout Jump Resistant Result Area
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .animateContentSize() // Animates container gracefully when password state changes
            ) {
                if (password.isNotEmpty()) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        // Display Box for newly created password
                        NovaWhiteCard(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 20.dp, vertical = 16.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = password,
                                    style = MaterialTheme.typography.bodyLarge,
                                    fontWeight = FontWeight.Medium,
                                    color = Color(0xFF1A1A1A),
                                    maxLines = 2,
                                    overflow = TextOverflow.Ellipsis,
                                    modifier = Modifier.weight(1f)
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                IconButton(
                                    onClick = onCopyClick,
                                    colors = IconButtonDefaults.iconButtonColors(
                                        contentColor = brandColor
                                    )
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.ContentCopy,
                                        contentDescription = "Copy Password"
                                    )
                                }
                            }
                        }

                        // Final Action: Save to Vault
                        Button(
                            onClick = onSaveClick,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(52.dp),
                            shape = RoundedCornerShape(14.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF2E7D32), // Direct Success Green Context
                                contentColor = Color.White
                            )
                        ) {
                            Text(
                                text = "Save Password to Vault",
                                style = MaterialTheme.typography.labelLarge,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
}