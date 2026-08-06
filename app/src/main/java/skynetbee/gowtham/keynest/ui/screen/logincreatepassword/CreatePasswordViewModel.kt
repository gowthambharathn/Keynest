package skynetbee.gowtham.keynest.ui.screen.logincreatepassword

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import skynetbee.gowtham.keynest.ui.screen.createpassword.CreatePasswordUiState
import skynetbee.gowtham.keynest.ui.screen.createpassword.Difficulty
import skynetbee.gowtham.keynest.ui.screen.createpassword.PasswordGenerator
import skynetbee.gowtham.keynest.data.security.CryptoManager
import skynetbee.gowtham.keynest.domain.model.Password
import skynetbee.gowtham.keynest.domain.usecase.SavePasswordUseCase
import javax.inject.Inject
import androidx.compose.runtime.*
/**
 * Created by Gowtham Barath
 * Date: 05-07-2026
 */

@HiltViewModel
class CreatePasswordViewModel @Inject constructor(
    private val cryptoManager: CryptoManager,
    private val savePasswordUseCase: SavePasswordUseCase
) : ViewModel() {

    var uiState by mutableStateOf(
        CreatePasswordUiState()
    )
        private set

    fun onTitleChanged(title: String) {
        uiState = uiState.copy(title = title)
    }

    fun onDifficultyChanged(
        difficulty: Difficulty
    ) {
        uiState = uiState.copy(
            difficulty = difficulty
        )
    }

    fun onLengthChanged(
        length: Float
    ) {
        uiState = uiState.copy(
            length = length
        )
    }

    fun generatePassword() {

        val password = PasswordGenerator.generate(
            length = uiState.length.toInt(),
            difficulty = uiState.difficulty
        )

        uiState = uiState.copy(
            generatedPassword = password
        )
    }

    fun savePassword() {

        if (uiState.title.isBlank()) return
        if (uiState.generatedPassword.isBlank()) return

        viewModelScope.launch {

            val (encryptedPassword, iv) =
                cryptoManager.encrypt(
                    uiState.generatedPassword
                )

            val password = Password(
                title = uiState.title,
                encryptedPassword = encryptedPassword,
                iv = iv
            )

            savePasswordUseCase(password)

            uiState = CreatePasswordUiState()

        }

    }

}