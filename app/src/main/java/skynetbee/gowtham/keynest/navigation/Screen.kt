package skynetbee.gowtham.keynest.navigation


/**
 * Created by Gowtham Barath
 * Date: 23-06-2026
 */

sealed class Screen(val route: String) {

    data object Splash : Screen("splash")

    data object AuthMethod : Screen("auth_method")

    data object CreatePassword : Screen("create_password")

    data object PasswordLogin : Screen("password_login")

    data object Biometric : Screen("biometric")

    data object Vault : Screen("vault")

    data object HomeScreen : Screen("homescreen")
}