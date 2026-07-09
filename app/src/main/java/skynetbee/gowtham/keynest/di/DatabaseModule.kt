package skynetbee.gowtham.keynest.di

/**
 * Created by Gowtham Barath
 * Date: 05-07-2026
 */

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import skynetbee.gowtham.keynest.data.local.KeyNestDatabase
import skynetbee.gowtham.keynest.data.local.PasswordDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): KeyNestDatabase {

        return Room.databaseBuilder(
            context,
            KeyNestDatabase::class.java,
            "keynest_database"
        ).build()
    }

    @Provides
    @Singleton
    fun providePasswordDao(
        database: KeyNestDatabase
    ): PasswordDao {

        return database.passwordDao()

    }

}