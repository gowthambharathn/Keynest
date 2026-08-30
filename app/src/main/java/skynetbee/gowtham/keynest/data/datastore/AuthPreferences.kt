package skynetbee.gowtham.keynest.data.datastore


/**
 * Created by Gowtham Barath
 * Date: 23-06-2026
 */

import kotlinx.coroutines.flow.Flow

interface AuthPreferences {

    suspend fun saveAuthMethod(authMethod: AuthMethod)

    fun getAuthMethod(): Flow<AuthMethod>

    suspend fun savePasswordHash(hash: String)

    suspend fun getPasswordHash(): String?

    suspend fun clear()
}