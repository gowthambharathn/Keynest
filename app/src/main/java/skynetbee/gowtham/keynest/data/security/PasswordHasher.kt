package skynetbee.gowtham.keynest.data.security


/**
 * Created by Gowtham Barath
 * Date: 23-06-2026
 */

import java.security.MessageDigest
import javax.inject.Inject

class PasswordHasher @Inject constructor() {

    fun hash(password: String): String {
        val bytes = MessageDigest
            .getInstance("SHA-256")
            .digest(password.toByteArray())

        return bytes.joinToString("") {
            "%02x".format(it)
        }
    }

    fun verify(
        password: String,
        storedHash: String
    ): Boolean {
        return hash(password) == storedHash
    }
}