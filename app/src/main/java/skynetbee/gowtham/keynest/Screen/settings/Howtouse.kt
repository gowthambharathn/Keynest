package skynetbee.gowtham.keynest.Screen.settings

/**
 * Created by Gowtham Barath
 */

import androidx.compose.foundation.background
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Lock
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
import androidx.navigation.NavHostController
import infinity.developers.coreutils.Ui.Nova.Components.GlowPosition
import infinity.developers.coreutils.Ui.Nova.Components.NovaBackground
import infinity.developers.coreutils.Ui.Nova.Components.NovaCard

private val TestAccentColor = Color(0xFF2196F3)

data class KeyNestGuideStep(
    val stepNumber: String,
    val title: String,
    val description: String,
    val icon: ImageVector
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HowToUseScreen(
    navController: NavHostController
) {
    val steps = listOf(
        KeyNestGuideStep(
            stepNumber = "01",
            title = "Add & Encrypt Accounts",
            description = "Tap the floating '+' button on the home screen. Enter your account title, username/email, password, and category. KeyNest encrypts the data locally before storing it.",
            icon = Icons.Default.Add
        ),
        KeyNestGuideStep(
            stepNumber = "02",
            title = "Copy Passwords Securely",
            description = "Tap the copy icon on any password card to quickly paste credentials into apps or websites. The clipboard notification confirms secure copying.",
            icon = Icons.Default.ContentCopy
        ),
        KeyNestGuideStep(
            stepNumber = "03",
            title = "Filter & Search Vault",
            description = "Use the top search bar or category toggles (Social, Work, Finance, Personal) to immediately filter down your stored accounts.",
            icon = Icons.Default.FilterList
        ),
        KeyNestGuideStep(
            stepNumber = "04",
            title = "Lock Your Vault",
            description = "To keep your vault secure, use the Lock button in settings or header to lock KeyNest immediately. Unlocking requires your local master auth or biometrics.",
            icon = Icons.Default.Lock
        )
    )

    NovaBackground(position = GlowPosition.TOP_RIGHT) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "How to Use KeyNest",
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
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp, vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                // Overview Card
                NovaCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Text(
                            text = "Welcome to KeyNest Vault",
                            color = Color.White,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "Learn how to manage your passwords, organize categories, and maintain local database security effortlessly.",
                            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.85f),
                            fontSize = 14.sp,
                            lineHeight = 22.sp
                        )
                    }
                }

                // Guide Steps
                steps.forEach { step ->
                    StepGuideCard(step = step)
                }

                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
private fun StepGuideCard(step: KeyNestGuideStep) {
    NovaCard(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
    ) {
        Column(
            modifier = Modifier.padding(18.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        color = TestAccentColor.copy(alpha = 0.15f),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.size(42.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = step.icon,
                                contentDescription = null,
                                tint = TestAccentColor,
                                modifier = Modifier.size(22.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Text(
                        text = step.title,
                        color = Color.White,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(TestAccentColor.copy(alpha = 0.2f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = step.stepNumber,
                        color = TestAccentColor,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = step.description,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.88f),
                fontSize = 14.sp,
                lineHeight = 22.sp
            )
        }
    }
}