package skynetbee.gowtham.keynest.data.local

/**
 * Created by Gowtham Barath
 * Date: 05-07-2026
 */

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PasswordDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(
        password: PasswordEntity
    )

    @Query("SELECT * FROM passwords ORDER BY createdAt DESC")
    fun getPasswords(): Flow<List<PasswordEntity>>

    @Query("DELETE FROM passwords WHERE id = :id")
    suspend fun delete(id: Long)
}