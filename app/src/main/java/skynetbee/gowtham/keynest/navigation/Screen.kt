package skynetbee.gowtham.keynest.navigation

sealed class Screen(val route: String) {
    object SetupMasterPassword : Screen("setup_master_password")
    object UnlockVault : Screen("unlock_vault")
    object Home : Screen("home_screen")

    object Settings: Screen("settings_screen")
    object AboutApp: Screen("aboutapp_screen")
    object AboutDeveloper: Screen("about_developer_screen")
    object Howtouse: Screen("howtouse_screen")
    object PrivacyPolicy: Screen("privacypolicy_screen")

    object ForgotPassword: Screen("forgotpassword")
    object addpassword : Screen("add_password")
    object AddEditPassword : Screen("add_edit_password?passwordId={passwordId}") {
        fun createRoute(passwordId: String? = null): String {
            return if (passwordId != null) {
                "add_edit_password?passwordId=$passwordId"
            } else {
                "add_edit_password"
            }
        }
    }
}