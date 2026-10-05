package skynetbee.gowtham.keynest.Screen.settings

/**
 * Created by Gowtham Barath
 */

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
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Lock
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutAppScreen(
    navController: NavController
) {
    NovaBackground(position = GlowPosition.TOP_RIGHT) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "About App",
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

                // App Intro Card
                NovaCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Surface(
                            color = TestAccentColor.copy(alpha = 0.15f),
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier.size(64.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Default.Key,
                                    contentDescription = "KeyNest Icon",
                                    tint = TestAccentColor,
                                    modifier = Modifier.size(36.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "KeyNest",
                            color = Color.White,
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = "Secure Encrypted Vault",
                            color = TestAccentColor,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "KeyNest is an offline-first password management app designed to protect your sensitive credentials locally on your device with hardware-backed key storage and zero cloud telemetry.",
                            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.9f),
                            fontSize = 14.sp,
                            lineHeight = 22.sp
                        )
                    }
                }

                // Core KeyNest Features
                FeatureCard(
                    icon = Icons.Default.Lock,
                    title = "Encrypted Local Storage",
                    description = "Credentials and sensitive notes are secured using SQLCipher database encryption paired with Android KeyStore."
                )

                FeatureCard(
                    icon = Icons.Default.Fingerprint,
                    title = "Biometric Security",
                    description = "Unlock your vault instantly and securely using hardware fingerprint or facial recognition integration."
                )

                FeatureCard(
                    icon = Icons.Default.Category,
                    title = "Smart Organization",
                    description = "Filter passwords seamlessly across Social, Work, Finance, and Personal categories."
                )

                FeatureCard(
                    icon = Icons.Default.ContentCopy,
                    title = "Instant Clipboard Actions",
                    description = "Copy passwords quickly with built-in UI feedback and automatic secure state management."
                )

                // Application Information
                NovaCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Text(
                            text = "Application Information",
                            color = TestAccentColor,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        AppInfoRow(label = "Version", value = "1.0.0")
                        AppInfoRow(label = "Security Spec", value = "AES-256 / Android KeyStore")
                        AppInfoRow(label = "Platform & UI", value = "Android / Jetpack Compose")
                        AppInfoRow(label = "Storage", value = "Encrypted Local Database")
                        AppInfoRow(label = "Developer", value = "Gowtham Barath N")
                    }
                }

                // Footer
                NovaCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Thank You For Using KeyNest",
                            color = Color.White,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Dedicated to delivering zero-trust, local security for all your online account credentials.",
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontSize = 13.sp,
                            lineHeight = 20.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
private fun FeatureCard(
    icon: ImageVector,
    title: String,
    description: String
) {
    NovaCard(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
    ) {
        Row(
            modifier = Modifier.padding(18.dp),
            verticalAlignment = Alignment.Top
        ) {
            Surface(
                color = TestAccentColor.copy(alpha = 0.15f),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.size(42.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = icon,
                        contentDescription = title,
                        tint = TestAccentColor,
                        modifier = Modifier.size(22.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = description,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.88f),
                    fontSize = 13.sp,
                    lineHeight = 21.sp
                )
            }
        }
    }
}

@Composable
private fun AppInfoRow(
    label: String,
    value: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
    ) {
        Text(
            text = label,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontSize = 13.sp
        )

        Spacer(modifier = Modifier.height(2.dp))

        Text(
            text = value,
            color = Color.White,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}