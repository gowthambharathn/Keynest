package skynetbee.gowtham.keynest.auth.splash

/**
 * Created by Gowtham Barath
 * Date: 23-06-2026
 */

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import skynetbee.gowtham.keynest.domain.model.AuthMethod

private const val TAG = "SplashScreen"

@Composable
fun SplashScreen(
    viewModel: SplashViewModel,
    navController: NavController
) {

    val authMethod by viewModel.authMethod.collectAsState()

    Log.d(TAG, "Current authMethod: $authMethod")

    LaunchedEffect(authMethod) {

        Log.d(TAG, "LaunchedEffect triggered with authMethod = $authMethod")

        when (authMethod) {

            AuthMethod.NONE -> {
                Log.d(TAG, "Navigating to auth_method")

                navController.navigate("auth_method") {
                    popUpTo("splash") {
                        inclusive = true
                    }
                }

                Log.d(TAG, "Navigation completed -> auth_method")
            }

            AuthMethod.PASSWORD -> {
                Log.d(TAG, "Navigating to password_login")

                navController.navigate("password_login") {
                    popUpTo("splash") {
                        inclusive = true
                    }
                }

                Log.d(TAG, "Navigation completed -> password_login")
            }

            AuthMethod.BIOMETRIC -> {
                Log.d(TAG, "Navigating to biometric")

                navController.navigate("biometric") {
                    popUpTo("splash") {
                        inclusive = true
                    }
                }

                Log.d(TAG, "Navigation completed -> biometric")
            }
        }
    }
}