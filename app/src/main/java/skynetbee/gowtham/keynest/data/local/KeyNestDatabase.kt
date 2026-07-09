package skynetbee.gowtham.keynest.data.local

/**
 * Created by Gowtham Barath
 * Date: 05-07-2026
 */

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [PasswordEntity::class],
    version = 1,
    exportSchema = false
)
abstract class KeyNestDatabase : RoomDatabase() {

    abstract fun passwordDao(): PasswordDao
}