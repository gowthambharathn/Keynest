package skynetbee.gowtham.keynest.data.security


/**
 * Created by Gowtham Barath
 * Date: 23-06-2026
 */

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.inject.Inject

class CryptoManager @Inject constructor() {

    companion object {
        private const val KEY_ALIAS = "keynest_secret_key"
        private const val ANDROID_KEYSTORE = "AndroidKeyStore"
        private const val TRANSFORMATION =
            "AES/GCM/NoPadding"
    }

    private val keyStore = KeyStore
        .getInstance(ANDROID_KEYSTORE)
        .apply { load(null) }

    private fun getOrCreateKey(): SecretKey {

        val existingKey = keyStore.getKey(
            KEY_ALIAS,
            null
        ) as? SecretKey

        if (existingKey != null) {
            return existingKey
        }

        val keyGenerator = KeyGenerator.getInstance(
            KeyProperties.KEY_ALGORITHM_AES,
            ANDROID_KEYSTORE
        )

        val keySpec = KeyGenParameterSpec.Builder(
            KEY_ALIAS,
            KeyProperties.PURPOSE_ENCRYPT or
                    KeyProperties.PURPOSE_DECRYPT
        )
            .setBlockModes(
                KeyProperties.BLOCK_MODE_GCM
            )
            .setEncryptionPaddings(
                KeyProperties.ENCRYPTION_PADDING_NONE
            )
            .build()

        keyGenerator.init(keySpec)

        return keyGenerator.generateKey()
    }

    fun encrypt(data: String): Pair<ByteArray, ByteArray> {

        val cipher = Cipher.getInstance(
            TRANSFORMATION
        )

        cipher.init(
            Cipher.ENCRYPT_MODE,
            getOrCreateKey()
        )

        val encryptedBytes =
            cipher.doFinal(data.toByteArray())

        return Pair(
            encryptedBytes,
            cipher.iv
        )
    }

    fun decrypt(
        encryptedData: ByteArray,
        iv: ByteArray
    ): String {

        val cipher = Cipher.getInstance(
            TRANSFORMATION
        )

        val spec =
            javax.crypto.spec.GCMParameterSpec(
                128,
                iv
            )

        cipher.init(
            Cipher.DECRYPT_MODE,
            getOrCreateKey(),
            spec
        )

        val decryptedBytes =
            cipher.doFinal(encryptedData)

        return String(decryptedBytes)
    }
}