package skynetbee.gowtham.keynest.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import net.zetetic.database.sqlcipher.SupportOpenHelperFactory

@Database(
    entities = [PasswordEntity::class],
    version = 1,
    exportSchema = false
)
abstract class KeyNestDatabase : RoomDatabase() {

    abstract fun passwordDao(): PasswordDao

    companion object {

        @Volatile
        private var INSTANCE: KeyNestDatabase? = null

        fun getDatabase(context: Context): KeyNestDatabase {
            return INSTANCE ?: synchronized(this) {

                System.loadLibrary("sqlcipher")

                val passphrase =
                    "KeyNest_Secure_Passphrase_Key".toByteArray()

                val factory =
                    SupportOpenHelperFactory(passphrase)

                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    KeyNestDatabase::class.java,
                    "keynest_secure.db"
                )
                    .openHelperFactory(factory)
                    .fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}