package skynetbee.gowtham.keynest.EngineStarter

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

// Nova UI Imports
import infinity.developers.coreutils.Ui.Nova.Components.GlowPosition
import infinity.developers.coreutils.Ui.Nova.Components.NovaBackground
import skynetbee.gowtham.keynest.Navigation.EngineStarterState
import skynetbee.gowtham.keynest.Navigation.EngineStarterViewModel
import skynetbee.gowtham.keynest.Utils.NovaLoadingIndicator
import skynetbee.gowtham.keynest.navigation.KeyNestNavigation

@Composable
fun EngineStarter(
    viewModel: EngineStarterViewModel = viewModel()
) {
    val engineState by viewModel.engineState.collectAsState()

    AnimatedContent(
        targetState = engineState,
        transitionSpec = {
            fadeIn() togetherWith fadeOut()
        },
        label = "EngineStarterTransition"
    ) { state ->
        when (state) {
            is EngineStarterState.Loading -> {
                EngineStarterLoadingScreen()
            }
            is EngineStarterState.Success -> {
                KeyNestNavigation(startDestination = state.startDestination)
            }
        }
    }
}

@Composable
private fun EngineStarterLoadingScreen() {
    NovaBackground(position = GlowPosition.TOP_RIGHT) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Default.Shield,
                contentDescription = "KeyNest Shield",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(72.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "KeyNest",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(32.dp))

            NovaLoadingIndicator(
                size = 48.dp,
                strokeWidth = 4.dp,
                primaryColor = MaterialTheme.colorScheme.primary,
                secondaryColor = MaterialTheme.colorScheme.surfaceVariant
            )
        }
    }
}