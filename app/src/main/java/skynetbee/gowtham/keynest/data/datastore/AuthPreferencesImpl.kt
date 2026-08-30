package skynetbee.gowtham.keynest.data.datastore


/**
 * Created by Gowtham Barath
 * Date: 23-06-2026
 */

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AuthPreferencesImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : AuthPreferences {

    companion object {
        private val AUTH_METHOD_KEY =
            stringPreferencesKey("auth_method")

        private val PASSWORD_HASH_KEY =
            stringPreferencesKey("password_hash")
    }

    override suspend fun saveAuthMethod(authMethod: AuthMethod) {
        dataStore.edit { preferences ->
            preferences[AUTH_METHOD_KEY] = authMethod.name
        }
    }

    override fun getAuthMethod(): Flow<AuthMethod> {
        return dataStore.data.map { preferences ->
            val method = preferences[AUTH_METHOD_KEY]
                ?: AuthMethod.NONE.name

            AuthMethod.valueOf(method)
        }
    }

    override suspend fun savePasswordHash(hash: String) {
        dataStore.edit { preferences ->
            preferences[PASSWORD_HASH_KEY] = hash
        }
    }

    override suspend fun getPasswordHash(): String? {
        return dataStore.data.map { preferences ->
            preferences[PASSWORD_HASH_KEY]
        }.firstOrNull()
    }

    override suspend fun clear() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }
}