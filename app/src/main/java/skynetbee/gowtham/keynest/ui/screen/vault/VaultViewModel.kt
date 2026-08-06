package skynetbee.gowtham.keynest.ui.screen.vault

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import skynetbee.gowtham.keynest.domain.usecase.DeletePasswordUseCase
import skynetbee.gowtham.keynest.domain.usecase.GetPasswordsUseCase
import javax.inject.Inject
import androidx.compose.runtime.*

/**
 * Created by Gowtham Barath
 * Date: 05-07-2026
 */

@HiltViewModel
class VaultViewModel @Inject constructor(

    private val getPasswordsUseCase: GetPasswordsUseCase,

    private val deletePasswordUseCase: DeletePasswordUseCase

) : ViewModel() {

    var uiState by mutableStateOf(
        VaultUiState()
    )
        private set

    init {

        loadPasswords()

    }

    private fun loadPasswords() {

        viewModelScope.launch {

            getPasswordsUseCase().collectLatest {

                uiState = uiState.copy(
                    passwords = it.map { password ->
                        PasswordCard(
                            id = password.id,
                            title = password.title,
                            password = String(password.encryptedPassword)
                        )
                    },
                    isLoading = false
                )

            }

        }

    }

    fun deletePassword(id: Long) {

        viewModelScope.launch {

            deletePasswordUseCase(id)

        }

    }

    fun onSearchChanged(search: String){

    }

    fun copyPassword(password: String){

    }

}