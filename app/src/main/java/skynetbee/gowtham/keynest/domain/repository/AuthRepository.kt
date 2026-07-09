package skynetbee.gowtham.keynest.domain.repository


/**
 * Created by Gowtham Barath
 * Date: 23-06-2026
 */
import kotlinx.coroutines.flow.Flow
import skynetbee.gowtham.keynest.domain.model.AuthMethod

interface AuthRepository {

    suspend fun saveAuthMethod(authMethod: AuthMethod)

    fun getAuthMethod(): Flow<AuthMethod>

    suspend fun savePasswordHash(hash: String)

    suspend fun getPasswordHash(): String?

    suspend fun clearAuthData()
}