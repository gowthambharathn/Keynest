package skynetbee.gowtham.keynest.navigation

/**
 * Created by Gowtham Barath
 * Date: 23-06-2026
 */

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object MasterPasswordSetup : Screen("master_password_setup")
    object PasswordLogin : Screen("password_login")
    object Biometric : Screen("biometric")
    object HomeScreen : Screen("home")
    object CreatePassword : Screen("create_password")
    object Vault : Screen("vault")
}