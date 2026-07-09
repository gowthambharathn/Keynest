package skynetbee.gowtham.keynest.auth.homescreen

/**
 * Created by Gowtham Barath
 * Date: 23-06-2026
 */

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import infinity.developers.coreutils.Ui.Nova.Components.Background.NovaWhiteBackground
import skynetbee.gowtham.keynest.auth.setup.AuthCard

@Composable
fun HomeScreen(
    onCreatePasswordClick: () -> Unit,
    onVaultClick: () -> Unit
) {

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        NovaWhiteBackground()

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

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Choose what you want to do",
                style = MaterialTheme.typography.titleMedium,
                color = Color(0xFF03A9F4).copy(alpha = 0.65f)
            )

            Spacer(modifier = Modifier.height(48.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                AuthCard(
                    modifier = Modifier.weight(1f),
                    icon = Icons.Default.AddCircle,
                    title = "Create",
                    onClick = onCreatePasswordClick
                )

                AuthCard(
                    modifier = Modifier.weight(1f),
                    icon = Icons.Default.Lock,
                    title = "Vault",
                    onClick = onVaultClick
                )
            }
        }
    }
}