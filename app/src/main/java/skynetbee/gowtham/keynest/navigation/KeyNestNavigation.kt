package skynetbee.gowtham.keynest.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import skynetbee.gowtham.keynest.Screen.CreateMasterPasswordScreen
import skynetbee.gowtham.keynest.Screen.addpassword.AddPasswordScreen
import skynetbee.gowtham.keynest.Screen.homescreen.HomeScreen
import skynetbee.gowtham.keynest.Screen.settings.AboutAppScreen
import skynetbee.gowtham.keynest.Screen.settings.AboutDeveloperScreen
import skynetbee.gowtham.keynest.Screen.settings.HowToUseScreen
import skynetbee.gowtham.keynest.Screen.settings.PrivacyPolicyScreen
import skynetbee.gowtham.keynest.Screen.settings.SettingsScreen
import skynetbee.gowtham.keynest.Screen.vaultunlock.UnlockVaultScreen

@Composable
fun KeyNestNavigation(
    startDestination: String,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(route = Screen.SetupMasterPassword.route) {
            CreateMasterPasswordScreen(
                onSetupComplete = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.SetupMasterPassword.route) { inclusive = true }
                    }
                }
            )
        }

        // Return Launch - Unlock Vault
        composable(route = Screen.UnlockVault.route) {
            UnlockVaultScreen(
                onUnlocked = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.UnlockVault.route) { inclusive = true }
                    }
                }
            )
        }

        composable(route = Screen.addpassword.route) {
            AddPasswordScreen(
                onNavigateBack = {
                    // Pop back to Home Screen when back icon is clicked
                    navController.popBackStack()
                },
                onSaveSuccess = {
                    // Pop back to Home Screen after password is saved
                    navController.popBackStack()
                }
            )
        }

        // Home Password List Screen
        composable(route = Screen.Home.route) {
            HomeScreen(
                onAddNewPasswordClick = {
                    navController.navigate(Screen.addpassword.route)
                },
                onEditPasswordClick = { passwordId ->
                    //navController.navigate(Screen.AddEditPassword.createRoute(passwordId))
                },
                onLockVaultClick = {
                    navController.navigate(Screen.UnlockVault.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                },
                onSettingsClick = {
                    navController.navigate(Screen.Settings.route)
                }
            )
        }
        composable(route = Screen.Settings.route) {
            SettingsScreen(
                navController = navController,
                onLockVaultClick = {
                    navController.navigate(Screen.UnlockVault.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                }
            )
        }
        composable(route = Screen.AboutApp.route) {
            AboutAppScreen(
                navController = navController
            )
        }
        composable(route = Screen.AboutDeveloper.route) {
            AboutDeveloperScreen(
                navController = navController
            )
        }
        composable(route = Screen.Howtouse.route) {
            HowToUseScreen(
                navController = navController
            )
        }
        composable(route = Screen.PrivacyPolicy.route) {
            PrivacyPolicyScreen(
                navController = navController
            )
        }
    }
}