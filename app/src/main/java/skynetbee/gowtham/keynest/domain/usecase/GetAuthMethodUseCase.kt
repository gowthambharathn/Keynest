package skynetbee.gowtham.keynest.domain.usecase


/**
 * Created by Gowtham Barath
 * Date: 23-06-2026
 */
import skynetbee.gowtham.keynest.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAuthMethodUseCase @Inject constructor(
    private val repository: AuthRepository
) {

    operator fun invoke(): Flow<AuthMethod> {
        return repository.getAuthMethod()
    }
}