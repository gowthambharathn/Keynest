package skynetbee.gowtham.keynest.ui.screen.setup

/**
 * Created by Gowtham Barath
 * Date: 23-06-2026
 */

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import infinity.developers.coreutils.Ui.Nova.Components.Background.NovaWhiteBackground
import skynetbee.gowtham.keynest.domain.model.AuthMethod

private const val TAG = "AuthMethodScreen"

@Composable
fun AuthMethodScreen(
    onPasswordSelected: () -> Unit,
    onBiometricSelected: () -> Unit,
    viewModel: AuthMethodViewModel
) {

    Box(modifier = Modifier.fillMaxSize()) {

        // Background
        NovaWhiteBackground()

        // Foreground UI
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
                text = "Your secure password vault",
                style = MaterialTheme.typography.titleMedium,
                color = Color(0xFF03A9F4).copy(alpha = 0.4f)
            )

            Spacer(modifier = Modifier.height(40.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                AuthCard(
                    modifier = Modifier.weight(1f),
                    icon = Icons.Default.Lock,
                    title = "Password"
                ) {
                    Log.d(TAG, "Master Password selected")
                    viewModel.selectAuthMethod(AuthMethod.PASSWORD)
                    onPasswordSelected()
                }

                AuthCard(
                    modifier = Modifier.weight(1f),
                    icon = Icons.Default.Fingerprint,
                    title = "Biometric"
                ) {
                    Log.d(TAG, "Biometric selected")
                    viewModel.selectAuthMethod(AuthMethod.BIOMETRIC)
                    onBiometricSelected()
                }
            }
        }
    }
}

@Composable
fun AuthCard(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    title: String,
    onClick: () -> Unit
) {

    Card(
        modifier = modifier
            .aspectRatio(1f)
            .clickable { onClick() },
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Surface(
                shape = RoundedCornerShape(50),
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.12f)
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier
                        .padding(18.dp)
                        .size(42.dp),
                    tint = Color(0xFF03A9F4)
                )
            }

            Spacer(modifier = Modifier.height(18.dp))

            Text(
                text = title,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}