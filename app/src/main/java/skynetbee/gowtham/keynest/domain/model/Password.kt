package skynetbee.gowtham.keynest.domain.model

/**
 * Created by Gowtham Barath
 * Date: 05-07-2026
 */

data class Password(

    val id: Long = 0,

    val title: String,

    val encryptedPassword: ByteArray,

    val iv: ByteArray,

    val createdAt: Long = System.currentTimeMillis()
)