package skynetbee.gowtham.keynest.auth.vault

/**
 * Created by Gowtham Barath
 * Date: 05-07-2026
 */

data class VaultUiState(
    val search: String = "",
    val passwords: List<PasswordCard> = emptyList(),
    val isLoading: Boolean = true
)