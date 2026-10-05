package skynetbee.gowtham.keynest.data.state

import skynetbee.gowtham.keynest.Screen.MasterPasswordStrength

data class AddPasswordUiState(
    val title: String = "",
    val password: String = "",
    val selectedCategoryIndex: Int = 0,
    val isPasswordVisible: Boolean = false,
    val strength: MasterPasswordStrength = MasterPasswordStrength.WEAK,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isSavedSuccess: Boolean = false,

    // --- Password generator state (new) ---
    val selectedStrengthIndex: Int = 0,
    val generatedPassword: String = "",
    val showGeneratedPasswordCard: Boolean = false
)