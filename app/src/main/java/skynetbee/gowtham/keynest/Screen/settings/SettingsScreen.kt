package skynetbee.gowtham.keynest.Screen.settings

import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PrivacyTip
import androidx.compose.material.icons.filled.Security
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import infinity.developers.coreutils.Ui.Nova.Components.GlowPosition
import infinity.developers.coreutils.Ui.Nova.Components.NovaBackground
import infinity.developers.coreutils.Ui.Nova.Components.NovaCard

private val TestAccentColor = Color(0xFF2196F3)

data class SettingItemModel(
    val title: String,
    val subtitle: String? = null,
    val icon: ImageVector,
    val route: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    navController: NavController,
    onLockVaultClick: () -> Unit = {}
) {
    val securityOptions = listOf(
        SettingItemModel(
            title = "Lock Vault",
            subtitle = "Immediately lock your encrypted vault",
            icon = Icons.Default.Lock,
            route = "lock_vault"
        ),
        SettingItemModel(
            title = "Security & Encryption",
            subtitle = "AES-256 & Android KeyStore configuration",
            icon = Icons.Default.Security,
            route = "security_settings"
        )
    )

    val aboutOptions = listOf(
        SettingItemModel(
            title = "Privacy Policy",
            subtitle = "Learn how your local data is protected",
            icon = Icons.Default.PrivacyTip,
            route = "privacypolicy"
        ),
        SettingItemModel(
            title = "About KeyNest",
            subtitle = "App version and repository info",
            icon = Icons.Default.Info,
            route = "aboutapp"
        ),
        SettingItemModel(
            title = "About Developer",
            subtitle = "Skynetbee / Gowtham Barath",
            icon = Icons.Default.Person,
            route = "aboutdeveloper"
        ),
        SettingItemModel(
            title = "Notifications",
            subtitle = "Security alerts & sync status",
            icon = Icons.Default.Notifications,
            route = "notification"
        ),
        SettingItemModel(
            title = "How to Use",
            subtitle = "Guide on managing vault records",
            icon = Icons.Default.HelpOutline,
            route = "howtouse"
        )
    )

    NovaBackground(position = GlowPosition.TOP_RIGHT) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "Settings",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 22.sp
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back",
                                tint = Color.White
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent
                    )
                )
            },
            containerColor = Color.Transparent
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp, vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                // Vault Security Section
                SectionHeader(title = "Vault Security")
                securityOptions.forEach { option ->
                    SettingCardItem(
                        item = option,
                        onClick = {
                            if (option.route == "lock_vault") {
                                onLockVaultClick()
                            } else {
                                navController.navigate(option.route)
                            }
                        }
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Information & About Section
                SectionHeader(title = "About & Information")
                aboutOptions.forEach { option ->
                    SettingCardItem(
                        item = option,
                        onClick = { navController.navigate(option.route) }
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // App Version Footer
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "KeyNest v1.0.0",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Secured with Local KeyStore Encryption",
                            style = MaterialTheme.typography.labelSmall,
                            color = TestAccentColor.copy(alpha = 0.7f)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
private fun SectionHeader(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleSmall,
        fontWeight = FontWeight.SemiBold,
        color = TestAccentColor,
        modifier = Modifier.padding(start = 4.dp, top = 8.dp)
    )
}

@Composable
private fun SettingCardItem(
    item: SettingItemModel,
    onClick: () -> Unit
) {
    NovaCard(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                Surface(
                    color = TestAccentColor.copy(alpha = 0.15f),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.size(40.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = null,
                            tint = TestAccentColor,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.width(14.dp))

                Column {
                    Text(
                        text = item.title,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Medium,
                        color = Color.White
                    )
                    item.subtitle?.let {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = "Navigate",
                tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
                modifier = Modifier.size(20.dp)
            )
        }
    }
}