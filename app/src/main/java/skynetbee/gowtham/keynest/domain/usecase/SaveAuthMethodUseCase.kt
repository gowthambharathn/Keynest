package skynetbee.gowtham.keynest.domain.usecase


/**
 * Created by Gowtham Barath
 * Date: 23-06-2026
 */
import skynetbee.gowtham.keynest.domain.model.AuthMethod
import skynetbee.gowtham.keynest.domain.repository.AuthRepository
import javax.inject.Inject

class SaveAuthMethodUseCase @Inject constructor(
    private val repository: AuthRepository
) {

    suspend operator fun invoke(authMethod: AuthMethod) {
        repository.saveAuthMethod(authMethod)
    }
}