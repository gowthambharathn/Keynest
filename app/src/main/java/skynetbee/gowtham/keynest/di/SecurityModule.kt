package skynetbee.gowtham.keynest.di


/**
 * Created by Gowtham Barath
 * Date: 23-06-2026
 */
import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import skynetbee.gowtham.keynest.data.security.BiometricHelper
import skynetbee.gowtham.keynest.data.security.CryptoManager
import skynetbee.gowtham.keynest.data.security.PasswordHasher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SecurityModule {

    @Provides
    @Singleton
    fun providePasswordHasher(): PasswordHasher {
        return PasswordHasher()
    }

    @Provides
    @Singleton
    fun provideCryptoManager(): CryptoManager {
        return CryptoManager()
    }

    @Provides
    @Singleton
    fun provideBiometricHelper(
        @ApplicationContext context: Context
    ): BiometricHelper {
        return BiometricHelper(context)
    }
}