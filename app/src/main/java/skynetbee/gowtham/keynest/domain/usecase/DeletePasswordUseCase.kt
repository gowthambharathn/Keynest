package skynetbee.gowtham.keynest.domain.usecase

/**
 * Created by Gowtham Barath
 * Date: 05-07-2026
 */

import skynetbee.gowtham.keynest.domain.repository.PasswordRepository
import javax.inject.Inject

class DeletePasswordUseCase @Inject constructor(
    private val repository: PasswordRepository
) {

    suspend operator fun invoke(
        id: Long
    ) {
        repository.delete(id)
    }
}