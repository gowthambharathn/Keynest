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
import skynetbee.gowtham.keynest.auth.biometric.BiometricScreen
import skynetbee.gowtham.keynest.auth.createpassword.CreatePasswordScreen
import skynetbee.gowtham.keynest.auth.createpassword.CreatePasswordViewModel
import skynetbee.gowtham.keynest.ui.screen.homescreen.HomeScreen
import skynetbee.gowtham.keynest.auth.password.CreatePasswordScreen
import skynetbee.gowtham.keynest.auth.password.PasswordLoginScreen
import skynetbee.gowtham.keynest.ui.screen.setup.AuthMethodScreen
import skynetbee.gowtham.keynest.ui.screen.splash.SplashScreen
import skynetbee.gowtham.keynest.auth.vault.VaultScreen
import skynetbee.gowtham.keynest.auth.vault.VaultViewModel

@Composable
fun NavGraph(
    navController: NavHostController
) {

    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {

        composable(Screen.Splash.route) {

            SplashScreen(
                viewModel = hiltViewModel(),
                navController = navController
            )
        }

        composable(Screen.AuthMethod.route) {

            AuthMethodScreen(
                viewModel = hiltViewModel(),
                onPasswordSelected = {
                    navController.navigate(
                        Screen.CreatePassword.route
                    )
                },
                onBiometricSelected = {
                    navController.navigate(
                        Screen.Biometric.route
                    )
                }
            )
        }

        composable(Screen.CreatePassword.route) {

            CreatePasswordScreen(
                viewModel = hiltViewModel(),
                onPasswordCreated = {
                    navController.navigate(
                        Screen.Vault.route
                    ) {
                        popUpTo(0)
                    }
                }
            )
        }

        composable(Screen.PasswordLogin.route) {

            PasswordLoginScreen(
                viewModel = hiltViewModel(),
                onLoginSuccess = {
                    navController.navigate(
                        Screen.HomeScreen.route
                    ) {
                        popUpTo(0)
                    }
                }
            )
        }

        composable(route = Screen.Biometric.route) {
            BiometricScreen(
                viewModel = hiltViewModel(),
                onAuthenticated = {
                    navController.navigate(Screen.HomeScreen.route) {
                        // Clear the backstack so pressing the back button doesn't take the user back to the lock screen
                        popUpTo(Screen.Biometric.route) {
                            inclusive = true
                        }
                    }
                },
                onFallbackToPassword = {
                    navController.navigate(Screen.PasswordLogin.route) {
                        // Pop the biometric screen if they switch to password login
                        popUpTo(Screen.Biometric.route) {
                            inclusive = true
                        }
                    }
                },
                modifier = Modifier.fillMaxSize()
            )
        }

        composable(Screen.HomeScreen.route) {

            HomeScreen(
                onCreatePasswordClick = {
                    navController.navigate("create_password")
                },
                onVaultClick = {
                    navController.navigate("vault")
                }
            )
        }
        composable(Screen.CreatePassword.route) {

            val viewModel: CreatePasswordViewModel = hiltViewModel()

            CreatePasswordScreen(

                title = viewModel.uiState.title,

                password = viewModel.uiState.generatedPassword,

                length = viewModel.uiState.length,

                selectedDifficulty = viewModel.uiState.difficulty,

                onTitleChange = viewModel::onTitleChanged,

                onDifficultyChange = viewModel::onDifficultyChanged,

                onLengthChange = viewModel::onLengthChanged,

                onGenerateClick = viewModel::generatePassword,

                onCopyClick = {
                    // Copy to clipboard (we'll add this next)
                },

                onSaveClick = {
                    viewModel.savePassword()
                    navController.popBackStack()
                }

            )

        }

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