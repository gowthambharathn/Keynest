package skynetbee.gowtham.keynest.di


/**
 * Created by Gowtham Barath
 * Date: 23-06-2026
 */
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import skynetbee.gowtham.keynest.data.datastore.AuthPreferences
import skynetbee.gowtham.keynest.data.datastore.AuthPreferencesImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Provides
    @Singleton
    fun provideDataStore(
        @ApplicationContext context: Context
    ): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            produceFile = {
                context.preferencesDataStoreFile(
                    "keynest_preferences"
                )
            }
        )
    }

    @Provides
    @Singleton
    fun provideAuthPreferences(
        dataStore: DataStore<Preferences>
    ): AuthPreferences {
        return AuthPreferencesImpl(dataStore)
    }
}