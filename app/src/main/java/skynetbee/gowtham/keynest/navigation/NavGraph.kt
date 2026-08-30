package skynetbee.gowtham.keynest.navigation

/**
 * Created by Gowtham Barath
 * Date: 23-06-2026
 */

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import skynetbee.gowtham.keynest.ui.screen.biometric.BiometricScreen
import skynetbee.gowtham.keynest.ui.screen.homescreen.HomeScreen
import skynetbee.gowtham.keynest.ui.screen.logincreatepassword.CreatePasswordScreen
import skynetbee.gowtham.keynest.ui.screen.logincreatepassword.CreatePasswordViewModel
import skynetbee.gowtham.keynest.ui.screen.passwordlogin.PasswordLoginScreen
import skynetbee.gowtham.keynest.ui.screen.setup.SetupMasterPasswordScreen
import skynetbee.gowtham.keynest.ui.screen.splash.SplashScreen
import skynetbee.gowtham.keynest.ui.screen.vault.VaultScreen
import skynetbee.gowtham.keynest.ui.screen.vault.VaultViewModel

@Composable
fun NavGraph(
    navController: NavHostController
) {

    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {

        // Splash Screen
        composable(Screen.Splash.route) {
            SplashScreen(
                viewModel = hiltViewModel(),
                navController = navController
            )
        }

        // Onboarding: Mandatory Master Password Creation + Optional Biometrics Setup
        composable(Screen.MasterPasswordSetup.route) {
            SetupMasterPasswordScreen(
                viewModel = hiltViewModel(),
                onSetupComplete = {
                    navController.navigate(Screen.HomeScreen.route) {
                        popUpTo(Screen.MasterPasswordSetup.route) { inclusive = true }
                    }
                }
            )
        }

        // Return User: Master Password Fallback Login
        composable(Screen.PasswordLogin.route) {
            PasswordLoginScreen(
                viewModel = hiltViewModel(),
                onLoginSuccess = {
                    navController.navigate(Screen.HomeScreen.route) {
                        popUpTo(Screen.PasswordLogin.route) { inclusive = true }
                    }
                }
            )
        }

        // Return User: Biometric Login Screen
        composable(route = Screen.Biometric.route) {
            BiometricScreen(
                viewModel = hiltViewModel(),
                onAuthenticated = {
                    navController.navigate(Screen.HomeScreen.route) {
                        popUpTo(Screen.Biometric.route) { inclusive = true }
                    }
                },
                onFallbackToPassword = {
                    navController.navigate(Screen.PasswordLogin.route) {
                        popUpTo(Screen.Biometric.route) { inclusive = true }
                    }
                },
                modifier = Modifier.fillMaxSize()
            )
        }

        // Main Home Dashboard Screen
        composable(Screen.HomeScreen.route) {
            HomeScreen(
                onCreatePasswordClick = {
                    navController.navigate(Screen.CreatePassword.route)
                },
                onVaultClick = {
                    navController.navigate(Screen.Vault.route)
                }
            )
        }

        // Vault Entry Generator Screen
        composable(Screen.CreatePassword.route) {
            val viewModel: CreatePasswordViewModel = hiltViewModel()

            CreatePasswordScreen(
                viewModel = viewModel,
                onPasswordCreated = {
                    navController.popBackStack()
                }
            )
        }

        // Password Vault Overview Screen
        composable(Screen.Vault.route) {
            val viewModel: VaultViewModel = hiltViewModel()

            VaultScreen(
                search = viewModel.uiState.search,
                passwords = viewModel.uiState.passwords,
                onSearchChange = viewModel::onSearchChanged,
                onCopyClick = viewModel::copyPassword,
                onDeleteClick = viewModel::deletePassword
            )
        }
    }
}