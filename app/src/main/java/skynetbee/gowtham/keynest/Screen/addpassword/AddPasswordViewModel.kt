package skynetbee.gowtham.keynest.Screen.addpassword

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import skynetbee.gowtham.keynest.Screen.MasterPasswordStrength
import skynetbee.gowtham.keynest.data.local.KeyNestDatabase
import skynetbee.gowtham.keynest.data.local.PasswordEntity
import skynetbee.gowtham.keynest.data.state.AddPasswordUiState

class AddPasswordViewModel(application: Application) : AndroidViewModel(application) {

    private val passwordDao = KeyNestDatabase.getDatabase(application).passwordDao()

    private val _uiState = MutableStateFlow(AddPasswordUiState())
    val uiState: StateFlow<AddPasswordUiState> = _uiState.asStateFlow()

    val categories = listOf("Personal", "Social", "Work", "Finance")

    // Password generator difficulty tiers shown in the toggle above the Generate button
    val generationStrengths = listOf("Easy", "Medium", "Hard", "Extreme")

    fun onTitleChanged(title: String) {
        _uiState.update { it.copy(title = title, errorMessage = null) }
    }

    fun onPasswordChanged(password: String) {
        val strength = calculateStrength(password)
        _uiState.update {
            it.copy(
                password = password,
                strength = strength,
                errorMessage = null,
                // Once the user starts typing manually, hide the generated-password card
                // so it doesn't look out of sync with what they're editing.
                showGeneratedPasswordCard = false
            )
        }
    }

    fun onCategorySelected(index: Int) {
        _uiState.update { it.copy(selectedCategoryIndex = index) }
    }

    fun togglePasswordVisibility() {
        _uiState.update { it.copy(isPasswordVisible = !it.isPasswordVisible) }
    }

    /** Selects which difficulty tier ("Easy"/"Medium"/"Hard"/"Extreme") the generator uses. */
    fun onGenerationStrengthSelected(index: Int) {
        _uiState.update { it.copy(selectedStrengthIndex = index) }
    }

    /**
     * Generates a new password for the currently selected difficulty tier, drops it straight
     * into the password field (so Save works right away), and reveals the result card.
     */
    fun generatePassword() {
        val tier = generationStrengths.getOrElse(_uiState.value.selectedStrengthIndex) { "Medium" }
        val newPassword = createPassword(tier)

        _uiState.update {
            it.copy(
                generatedPassword = newPassword,
                password = newPassword,
                strength = calculateStrength(newPassword),
                showGeneratedPasswordCard = true,
                errorMessage = null
            )
        }
    }

    /** Called from the refresh icon on the generated-password card when the user isn't happy with it. */
    fun regeneratePassword() {
        generatePassword()
    }

    fun savePassword() {
        val state = _uiState.value

        when {
            state.title.isBlank() -> {
                _uiState.update { it.copy(errorMessage = "Account title is required.") }
                return
            }
            state.password.isBlank() -> {
                _uiState.update { it.copy(errorMessage = "Password is required.") }
                return
            }
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            try {
                val selectedCategory = categories.getOrElse(state.selectedCategoryIndex) { "Personal" }
                val trimmedTitle = state.title.trim()

                // 1. Check if record already exists in local database
                val isDuplicate = passwordDao.isDuplicatePassword(
                    title = trimmedTitle,
                    encryptedPassword = state.password
                )

                if (isDuplicate) {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = "This password record is already saved in your vault."
                        )
                    }
                    return@launch
                }

                // 2. Insert if it does not exist
                val newEntry = PasswordEntity(
                    title = trimmedTitle,
                    encryptedPassword = state.password,
                    category = selectedCategory,
                    isFavorite = false,
                    updatedAt = System.currentTimeMillis()
                )

                passwordDao.insertPassword(newEntry)

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        isSavedSuccess = true
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = "Failed to save entry: ${e.localizedMessage}"
                    )
                }
            }
        }
    }

    private fun calculateStrength(password: String): MasterPasswordStrength {
        if (password.length < 6) return MasterPasswordStrength.WEAK
        var score = 0
        if (password.length >= 10) score += 1
        if (password.length >= 14) score += 1
        if (password.any { it.isDigit() }) score += 1
        if (password.any { it.isUpperCase() }) score += 1
        if (password.any { !it.isLetterOrDigit() }) score += 1

        return when {
            score <= 1 -> MasterPasswordStrength.WEAK
            score in 2..3 -> MasterPasswordStrength.MEDIUM
            score == 4 -> MasterPasswordStrength.STRONG
            else -> MasterPasswordStrength.VERY_STRONG
        }
    }

    /**
     * Builds a random password for the given difficulty tier.
     * Each tier fixes a length and a character pool, and guarantees at least
     * one character from every set in that pool so the result actually
     * reflects the chosen difficulty (not just luck of the draw).
     */
    private fun createPassword(tier: String): String {
        val lower = "abcdefghijklmnopqrstuvwxyz"
        val upper = lower.uppercase()
        val digits = "0123456789"
        val symbols = "!@#$%^&*()_-+=?"

        val (length, requiredSets) = when (tier) {
            "Easy" -> 8 to listOf(lower, digits)
            "Medium" -> 12 to listOf(lower, upper, digits)
            "Hard" -> 16 to listOf(lower, upper, digits, symbols)
            "Extreme" -> 24 to listOf(lower, upper, digits, symbols)
            else -> 12 to listOf(lower, upper, digits)
        }

        val pool = requiredSets.joinToString("")

        // One guaranteed char from each required set...
        val mandatoryChars = requiredSets.map { it.random() }
        // ...then fill the rest randomly from the full pool.
        val remainingCount = (length - mandatoryChars.size).coerceAtLeast(0)
        val randomChars = (1..remainingCount).map { pool.random() }

        return (mandatoryChars + randomChars).shuffled().joinToString("")
    }
}