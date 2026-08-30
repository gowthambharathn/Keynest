package skynetbee.gowtham.keynest.domain.model

/**
 * Represents the method a user has configured to unlock the vault.
 *
 * This was referenced (but never declared) by:
 *  - data/datastore/AuthPreferences.kt
 *  - data/datastore/AuthPreferencesImpl.kt
 *  - data/repository/AuthRepositoryImpl.kt
 *  - domain/repository/AuthRepository.kt
 *  - domain/usecase/SaveAuthMethodUseCase.kt
 *  - domain/usecase/GetAuthMethodUseCase.kt
 * which caused "Unresolved reference: AuthMethod" build errors in all of them.
 */
enum class AuthMethod {
    NONE,
    PASSWORD,
    BIOMETRIC
}
