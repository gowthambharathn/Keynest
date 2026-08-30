package skynetbee.gowtham.keynest.ui.screen.splash

/**
 * Created by Gowtham Barath
 * Date: 23-06-2026
 */

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import infinity.developers.coreutils.Ui.Nova.Components.Background.NovaWhiteBackground

private const val TAG = "SplashScreen"

@Composable
fun SplashScreen(
    viewModel: SplashViewModel,
    navController: NavController
) {
    val destination by viewModel.destination.collectAsState()

    Log.d(TAG, "Current SplashDestination: $destination")

    LaunchedEffect(destination) {
        when (destination) {
            SplashDestination.Loading -> {
                Log.d(TAG, "Evaluating user state...")
            }
            SplashDestination.MasterPasswordSetup -> {
                Log.d(TAG, "First time user -> Navigating to master_password_setup")
                navController.navigate("master_password_setup") {
                    popUpTo("splash") { inclusive = true }
                }
            }
            SplashDestination.BiometricLogin -> {
                Log.d(TAG, "Returning user with Biometrics -> Navigating to biometric")
                navController.navigate("biometric") {
                    popUpTo("splash") { inclusive = true }
                }
            }
            SplashDestination.PasswordLogin -> {
                Log.d(TAG, "Returning user with Password -> Navigating to password_login")
                navController.navigate("password_login") {
                    popUpTo("splash") { inclusive = true }
                }
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        NovaWhiteBackground()
    }
}