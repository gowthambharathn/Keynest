package skynetbee.gowtham.keynest.Security

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import java.security.MessageDigest

class SecurityManager(private val context: Context) {

    private val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private val prefs = EncryptedSharedPreferences.create(
        context,
        "keynest_secure_prefs",
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    companion object {
        private const val KEY_MASTER_PASSWORD_HASH = "master_password_hash"
        private const val KEY_IS_VAULT_CREATED = "is_vault_created"
        private const val DATABASE_NAME = "keynest_vault.db" // Update with your actual Room/SQLCipher DB name if different
    }

    fun isMasterPasswordSet(): Boolean {
        return prefs.getBoolean(KEY_IS_VAULT_CREATED, false)
    }

    fun saveMasterPassword(password: String) {
        val hash = hashPassword(password)
        prefs.edit()
            .putString(KEY_MASTER_PASSWORD_HASH, hash)
            .putBoolean(KEY_IS_VAULT_CREATED, true)
            .apply()
    }

    fun verifyMasterPassword(password: String): Boolean {
        val savedHash = prefs.getString(KEY_MASTER_PASSWORD_HASH, null) ?: return false
        return savedHash == hashPassword(password)
    }

    /**
     * Completely wipes all encrypted preferences and deletes local vault database files.
     */
    fun clearAllData() {
        // 1. Clear EncryptedSharedPreferences
        prefs.edit().clear().apply()

        // 2. Delete local SQLite/SQLCipher database files
        context.deleteDatabase(DATABASE_NAME)
    }

    private fun hashPassword(password: String): String {
        val digest = MessageDigest.getInstance("SHA-256")
        val hashBytes = digest.digest(password.toByteArray(Charsets.UTF_8))
        return hashBytes.joinToString("") { "%02x".format(it) }
    }
}