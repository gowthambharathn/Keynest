package skynetbee.gowtham.keynest.domain.repository

/**
 * Created by Gowtham Barath
 * Date: 05-07-2026
 */

import kotlinx.coroutines.flow.Flow
import skynetbee.gowtham.keynest.domain.model.Password

interface PasswordRepository {

    suspend fun insert(
        password: Password
    )

    fun getPasswords(): Flow<List<Password>>

    suspend fun delete(
        id: Long
    )
}