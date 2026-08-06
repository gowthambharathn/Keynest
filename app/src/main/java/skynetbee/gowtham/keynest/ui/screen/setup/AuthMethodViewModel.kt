package skynetbee.gowtham.keynest.ui.screen.setup


/**
 * Created by Gowtham Barath
 * Date: 23-06-2026
 */
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import skynetbee.gowtham.keynest.domain.model.AuthMethod
import skynetbee.gowtham.keynest.domain.usecase.SaveAuthMethodUseCase
import javax.inject.Inject

@HiltViewModel
class AuthMethodViewModel @Inject constructor(
    private val saveAuthMethodUseCase: SaveAuthMethodUseCase
) : ViewModel() {

    fun selectAuthMethod(authMethod: AuthMethod) {
        viewModelScope.launch {
            saveAuthMethodUseCase(authMethod)
        }
    }
}