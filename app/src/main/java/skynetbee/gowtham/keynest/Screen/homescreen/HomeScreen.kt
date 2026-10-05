package skynetbee.gowtham.keynest.Screen.homescreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

import infinity.developers.coreutils.Ui.Nova.Components.GlowPosition
import infinity.developers.coreutils.Ui.Nova.Components.NovaBackground
import infinity.developers.coreutils.Ui.Nova.Components.NovaCard
import infinity.developers.coreutils.Ui.Nova.Components.NovaTextField
import infinity.developers.coreutils.Ui.Nova.Components.NovaToggle
import skynetbee.gowtham.keynest.data.local.PasswordEntity

private val TestAccentColor = Color(0xFF2196F3)

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = viewModel(),
    onAddNewPasswordClick: () -> Unit,
    onEditPasswordClick: (Long) -> Unit,
    onLockVaultClick: () -> Unit,
    onSettingsClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val passwords by viewModel.filteredPasswords.collectAsState()

    val categories = listOf("All", "Social", "Work", "Finance", "Personal")

    NovaBackground(position = GlowPosition.TOP_RIGHT) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp)
            ) {

                Spacer(modifier = Modifier.height(48.dp))

                // Vault Header Bar with Lock & Settings Icon
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Left: Lock Vault Button
                    Surface(
                        color = TestAccentColor.copy(alpha = 0.12f),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        IconButton(onClick = onLockVaultClick) {
                            Icon(
                                imageVector = Icons.Default.Lock,
                                contentDescription = "Lock Vault",
                                tint = TestAccentColor,
                                modifier = Modifier.size(22.dp)
                            )
                        }
                    }

                    // Center: KeyNest Title & Subtitle
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "KeyNest",
                            color = Color.White,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Secure Vault",
                            color = TestAccentColor,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }

                    // Right: Settings Button
                    Surface(
                        color = TestAccentColor.copy(alpha = 0.12f),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        IconButton(onClick = onSettingsClick) {
                            Icon(
                                imageVector = Icons.Default.Settings,
                                contentDescription = "Settings",
                                tint = Color.White,
                                modifier = Modifier.size(22.dp)
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))

                // Search Bar
                NovaTextField(
                    value = uiState.searchQuery,
                    onValueChange = { viewModel.onSearchQueryChanged(it) },
                    hint = "Search accounts...",
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = null,
                            tint = TestAccentColor
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))


                // Encrypted Passwords List
                if (passwords.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No vault records found.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            textAlign = TextAlign.Center
                        )
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        contentPadding = PaddingValues(bottom = 88.dp)
                    ) {
                        items(passwords, key = { it.id }) { item ->
                            PasswordItemCard(
                                item = item,
                                isCopied = uiState.copiedPasswordId == item.id,
                                onClick = { onEditPasswordClick(item.id) },
                                onCopyClick = { viewModel.copyPasswordToClipboard(item) },
                                onFavoriteClick = { viewModel.toggleFavorite(item) },
                                onDeleteClick = { viewModel.deletePassword(item.id) }
                            )
                        }
                    }
                }
            }

            // Floating Add Password Button
            FloatingActionButton(
                onClick = onAddNewPasswordClick,
                containerColor = TestAccentColor,
                contentColor = Color.White,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(24.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Password"
                )
            }
        }
    }
}

@Composable
private fun PasswordItemCard(
    item: PasswordEntity,
    isCopied: Boolean,
    onClick: () -> Unit,
    onCopyClick: () -> Unit,
    onFavoriteClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    NovaCard(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(38.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(TestAccentColor.copy(alpha = 0.15f))
                    ) {
                        Text(
                            text = item.title.firstOrNull()?.uppercase() ?: "P",
                            fontWeight = FontWeight.Bold,
                            color = TestAccentColor,
                            fontSize = 18.sp
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column {
                        Text(
                            text = item.title,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Text(
                            text = item.category,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = onFavoriteClick) {
                        Icon(
                            imageVector = if (item.isFavorite) Icons.Default.Star else Icons.Outlined.StarBorder,
                            contentDescription = "Favorite",
                            tint = if (item.isFavorite) Color(0xFFFFB300) else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    IconButton(onClick = onCopyClick) {
                        Icon(
                            imageVector = Icons.Default.ContentCopy,
                            contentDescription = "Copy Password",
                            tint = TestAccentColor
                        )
                    }

                    IconButton(onClick = onDeleteClick) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete",
                            tint = MaterialTheme.colorScheme.error.copy(alpha = 0.8f)
                        )
                    }
                }
            }

            AnimatedVisibility(visible = isCopied) {
                Text(
                    text = "Password copied securely to clipboard!",
                    style = MaterialTheme.typography.labelSmall,
                    color = TestAccentColor,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}