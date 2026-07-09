package skynetbee.gowtham.keynest.data.repository


/**
 * Created by Gowtham Barath
 * Date: 23-06-2026
 */
import kotlinx.coroutines.flow.Flow
import skynetbee.gowtham.keynest.data.datastore.AuthPreferences
import skynetbee.gowtham.keynest.domain.model.AuthMethod
import skynetbee.gowtham.keynest.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authPreferences: AuthPreferences
) : AuthRepository {

    override suspend fun saveAuthMethod(
        authMethod: AuthMethod
    ) {
        authPreferences.saveAuthMethod(authMethod)
    }

    override fun getAuthMethod(): Flow<AuthMethod> {
        return authPreferences.getAuthMethod()
    }

    override suspend fun savePasswordHash(
        hash: String
    ) {
        authPreferences.savePasswordHash(hash)
    }

    override suspend fun getPasswordHash(): String? {
        return authPreferences.getPasswordHash()
    }

    override suspend fun clearAuthData() {
        authPreferences.clear()
    }
}