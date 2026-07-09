package skynetbee.gowtham.keynest.domain.usecase


/**
 * Created by Gowtham Barath
 * Date: 23-06-2026
 */
import skynetbee.gowtham.keynest.data.security.PasswordHasher
import skynetbee.gowtham.keynest.domain.repository.AuthRepository
import javax.inject.Inject

class VerifyPasswordUseCase @Inject constructor(
    private val repository: AuthRepository,
    private val passwordHasher: PasswordHasher
) {

    suspend operator fun invoke(password: String): Boolean {

        val storedHash = repository.getPasswordHash()
            ?: return false

        return passwordHasher.verify(
            password = password,
            storedHash = storedHash
        )
    }
}