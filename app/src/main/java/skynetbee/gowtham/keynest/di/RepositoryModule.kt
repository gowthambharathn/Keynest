package skynetbee.gowtham.keynest.di

/**
 * Created by Gowtham Barath
 * Date: 23-06-2026
 */

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import skynetbee.gowtham.keynest.data.repository.AuthRepositoryImpl
import skynetbee.gowtham.keynest.data.repository.PasswordRepositoryImpl
import skynetbee.gowtham.keynest.domain.repository.AuthRepository
import skynetbee.gowtham.keynest.domain.repository.PasswordRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository

    @Binds
    @Singleton
    abstract fun bindPasswordRepository(
        passwordRepositoryImpl: PasswordRepositoryImpl
    ): PasswordRepository
}