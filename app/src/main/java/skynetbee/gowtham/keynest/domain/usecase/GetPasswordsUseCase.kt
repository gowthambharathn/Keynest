package skynetbee.gowtham.keynest.domain.usecase

/**
 * Created by Gowtham Barath
 * Date: 05-07-2026
 */

import kotlinx.coroutines.flow.Flow
import skynetbee.gowtham.keynest.domain.model.Password
import skynetbee.gowtham.keynest.domain.repository.PasswordRepository
import javax.inject.Inject

class GetPasswordsUseCase @Inject constructor(
    private val repository: PasswordRepository
) {

    operator fun invoke(): Flow<List<Password>> {
        return repository.getPasswords()
    }
}