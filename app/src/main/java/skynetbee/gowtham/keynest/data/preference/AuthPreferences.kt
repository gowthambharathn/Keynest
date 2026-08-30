package skynetbee.gowtham.keynest.data.preference


/**
 * Created by Gowtham Barath
 * Date: 23-06-2026
 */

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore by preferencesDataStore(name = "auth_preferences")

@Singleton
class AuthPreferences @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private object PreferencesKeys {
        val IS_MASTER_PASSWORD_SET = booleanPreferencesKey("is_master_password_set")
        val IS_BIOMETRIC_ENABLED = booleanPreferencesKey("is_biometric_enabled")
    }

    val isMasterPasswordSet: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[PreferencesKeys.IS_MASTER_PASSWORD_SET] ?: false
        }

    val isBiometricEnabled: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[PreferencesKeys.IS_BIOMETRIC_ENABLED] ?: false
        }

    suspend fun setMasterPasswordCreated(isSet: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.IS_MASTER_PASSWORD_SET] = isSet
        }
    }

    suspend fun setBiometricEnabled(enabled: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.IS_BIOMETRIC_ENABLED] = enabled
        }
    }
}