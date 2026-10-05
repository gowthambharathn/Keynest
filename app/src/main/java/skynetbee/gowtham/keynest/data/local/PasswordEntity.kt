package skynetbee.gowtham.keynest.data.local


import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "passwords")
data class PasswordEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val encryptedPassword: String,
    val category: String,
    val isFavorite: Boolean = false,
    val updatedAt: Long = System.currentTimeMillis()
)

@Dao
interface PasswordDao {

    @Query("SELECT * FROM passwords ORDER BY updatedAt DESC")
    fun getAllPasswords(): Flow<List<PasswordEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPassword(password: PasswordEntity)

    @Update
    suspend fun updatePassword(password: PasswordEntity)

    @Query("DELETE FROM passwords WHERE id = :id")
    suspend fun deletePasswordById(id: Long)

    @Query("SELECT * FROM passwords WHERE id = :id LIMIT 1")
    suspend fun getPasswordById(id: Long): PasswordEntity?
}