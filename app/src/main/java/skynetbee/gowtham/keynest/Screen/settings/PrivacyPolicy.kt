package skynetbee.gowtham.keynest.Screen.settings

/**
 * Created by Gowtham Barath
 */

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import infinity.developers.coreutils.Ui.Nova.Components.GlowPosition
import infinity.developers.coreutils.Ui.Nova.Components.NovaBackground
import infinity.developers.coreutils.Ui.Nova.Components.NovaCard
import skynetbee.gowtham.keynest.Utils.titleColor

private val TestAccentColor = Color(0xFF2196F3)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrivacyPolicyScreen(
    navController: NavController
) {
    NovaBackground(position = GlowPosition.TOP_RIGHT) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "Privacy Policy",
                            color = titleColor(),
                            fontWeight = FontWeight.Bold,
                            fontSize = 22.sp
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back",
                                tint = titleColor()
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

                // Header Overview Card
                NovaCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp)
                    ) {
                        Text(
                            text = "KeyNest Privacy Policy",
                            color = titleColor(),
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = "Last Updated: August 2026",
                            color = TestAccentColor,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Medium
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        Text(
                            text = "KeyNest is an offline-first password vault designed to keep your credentials completely secure. We believe your sensitive data belongs solely to you.",
                            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.9f),
                            fontSize = 14.sp,
                            lineHeight = 22.sp
                        )
                    }
                }

                // Security Policy Sections
                PolicySectionCard(
                    title = "Local Encrypted Data Storage",
                    content = "All account titles, usernames, passwords, and custom notes created inside KeyNest are encrypted locally on your device. Data is secured using industry-standard AES-256 / SQLCipher encryption combined with the hardware-backed Android KeyStore system."
                )

                PolicySectionCard(
                    title = "Zero Cloud & Server Telemetry",
                    content = "KeyNest operates entirely offline for credential storage. We do not host remote databases, nor do we track, transmit, upload, or sync your master keys or vault items to any cloud server or third-party service."
                )

                PolicySectionCard(
                    title = "Biometrics & Master Passwords",
                    content = "Biometric authentication (Fingerprint / Face unlock) and your Master Password operate directly against Android's local hardware prompt framework. KeyNest never stores your unencrypted Master Password in plain text."
                )

                PolicySectionCard(
                    title = "System Permissions",
                    content = "KeyNest asks strictly for essential permissions required for operation—such as USE_BIOMETRIC for vault unlock and Clipboard manager access for secure 30-second temporary password copying."
                )

                PolicySectionCard(
                    title = "Third-Party Sharing & Analytics",
                    content = "We do not sell, rent, share, or analyze user credential records with third-party advertisers, marketing partners, or data brokers. Your vault contents remain private to your local device."
                )

                PolicySectionCard(
                    title = "Contact & Developer Support",
                    content = "For questions or feedback regarding KeyNest's local encryption or security standards, contact the developer through official application support channels or developer listings."
                )

                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
private fun PolicySectionCard(
    title: String,
    content: String
) {
    NovaCard(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
    ) {
        Column(
            modifier = Modifier.padding(18.dp)
        ) {
            Text(
                text = title,
                color = TestAccentColor,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = content,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.88f),
                fontSize = 14.sp,
                lineHeight = 22.sp
            )
        }
    }
}