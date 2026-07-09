package skynetbee.gowtham.keynest.data.local

/**
 * Created by Gowtham Barath
 * Date: 05-07-2026
 */

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "passwords")
data class PasswordEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val title: String,

    val encryptedPassword: ByteArray,

    val iv: ByteArray,

    val createdAt: Long = System.currentTimeMillis()
)