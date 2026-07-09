package skynetbee.gowtham.keynest.auth.vault

/**
 * Created by Gowtham Barath
 * Date: 05-07-2026
 * Enhanced Vault UI Implementation
 */

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import infinity.developers.coreutils.Ui.Nova.Components.Background.NovaWhiteBackground
import infinity.developers.coreutils.Ui.Nova.Components.TextField.NovaWhiteTextField


@Composable
fun VaultScreen(
    search: String,
    passwords: List<PasswordCard>,
    onSearchChange: (String) -> Unit,
    onCopyClick: (String) -> Unit,
    onDeleteClick: (Long) -> Unit
) {
    val brandColor = Color(0xFF03A9F4)
    val neutralTextAlpha = brandColor.copy(alpha = 0.6f)

    Box(modifier = Modifier.fillMaxSize()) {
        NovaWhiteBackground()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            // Screen Header Title Context
            Text(
                text = "Your Vault",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = brandColor
            )

            Text(
                text = "Securely manage and quickly copy your stored account credentials.",
                style = MaterialTheme.typography.bodyMedium,
                color = neutralTextAlpha,
                modifier = Modifier.padding(top = 4.dp, bottom = 20.dp)
            )

            // Enhanced Search Bar wrapper utilizing Nova Custom Design System styling
            Box(modifier = Modifier.fillMaxWidth()) {
                NovaWhiteTextField(
                    value = search,
                    onValueChange = onSearchChange,
                    hint = "Search accounts...",
                    modifier = Modifier.fillMaxWidth(),
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search Icon",
                            tint = brandColor.copy(alpha = 0.5f)
                        )
                    }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Adaptive Empty vs Populated Content Layout Statuses
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                if (passwords.isEmpty()) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = if (search.isEmpty()) "No passwords saved yet." else "No matches found.",
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Medium,
                            color = brandColor.copy(alpha = 0.4f)
                        )
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(vertical = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(
                            items = passwords,
                            key = { it.id } // Optimizes structural performance updates inside LazyColumn
                        ) { item ->
                            VaultPasswordCardItem(
                                item = item,
                                brandColor = brandColor,
                                onCopyClick = onCopyClick,
                                onDeleteClick = onDeleteClick
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun VaultPasswordCardItem(
    item: PasswordCard,
    brandColor: Color,
    onCopyClick: (String) -> Unit,
    onDeleteClick: (Long) -> Unit
) {
    // Local masking visual state to safely preview contents in public spaces
    var isPasswordVisible by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = brandColor.copy(alpha = 0.03f)
        ),
        border = BorderStroke(
            width = 1.dp,
            color = brandColor.copy(alpha = 0.12f)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp)
        ) {
            // Label Header
            Text(
                text = item.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1A1A1A),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Password Content Layout (Monospace keeps characters uniformly proportional)
            Text(
                text = if (isPasswordVisible) item.password else "••••••••••••",
                style = MaterialTheme.typography.bodyLarge,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Medium,
                color = if (isPasswordVisible) Color(0xFF424242) else brandColor.copy(alpha = 0.4f),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            Divider(color = brandColor.copy(alpha = 0.08f), thickness = 1.dp)

            // Dynamic Action Tray
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Visibility Toggle Element
                IconButton(
                    onClick = { isPasswordVisible = !isPasswordVisible },
                    colors = IconButtonDefaults.iconButtonColors(contentColor = brandColor.copy(alpha = 0.7f))
                ) {
                    Icon(
                        imageVector = if (isPasswordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                        contentDescription = "Toggle Visibility"
                    )
                }

                // Copy Action Element
                IconButton(
                    onClick = { onCopyClick(item.password) },
                    colors = IconButtonDefaults.iconButtonColors(contentColor = brandColor)
                ) {
                    Icon(
                        imageVector = Icons.Default.ContentCopy,
                        contentDescription = "Copy Password string value"
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                // Delete Action Element
                IconButton(
                    onClick = { onDeleteClick(item.id) },
                    colors = IconButtonDefaults.iconButtonColors(contentColor = MaterialTheme.colorScheme.error.copy(alpha = 0.8f))
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Remove Password profile from vault database storage"
                    )
                }
            }
        }
    }
}