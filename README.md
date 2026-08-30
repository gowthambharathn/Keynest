# KeyNest — Project Documentation

KeyNest is an Android password manager app built with **Jetpack Compose**, **Hilt** (DI), **Room** (local database), and **DataStore** (preferences). It lets a user set a master password, optionally unlock with biometrics, generate/save passwords, and browse them in a vault.

This document lists **every file in the project** and what it does, followed by a summary of the build errors that were found and fixed.

---

## 🔧 Build Errors Found & Fixed

| # | Error | Where | Fix |
|---|-------|-------|-----|
| 1 | `Unresolved reference: AuthMethod` | `AuthPreferences.kt`, `AuthPreferencesImpl.kt`, `AuthRepository.kt`, `AuthRepositoryImpl.kt`, `SaveAuthMethodUseCase.kt`, `GetAuthMethodUseCase.kt` | The `AuthMethod` enum was used everywhere but never declared. Created `domain/model/AuthMethod.kt` (`NONE`, `PASSWORD`, `BIOMETRIC`) and added the missing `import` to each file. |
| 2 | `Unresolved reference: AuthCard` | `BiometricScreen.kt` (imports `ui.screen.setup.AuthCard`) | `AuthCard` composable was never defined. Created `ui/screen/setup/AuthCard.kt` — a simple selectable icon+label card. |
| 3 | `Unresolved reference: NavHost / composable / NavHostController` | `MainActivity.kt`, `navigation/NavGraph.kt` | The Gradle dependency `androidx.navigation:navigation-compose` was missing (only `hilt-navigation-compose` was declared). Added it to `gradle/libs.versions.toml` and `app/build.gradle.kts`. |
| 4 | Missing runtime permission | `AndroidManifest.xml` | `android.permission.USE_BIOMETRIC` was not declared, needed for `BiometricPrompt`. Added it. |

**Note on duplicate/orphaned code:** the project contains two parallel, unconnected implementations for both "auth preferences" and "setup master password screen" (see the "Duplicate / Unused Code" section below). Only one of each pair is actually wired into navigation — the other compiles (once the `AuthMethod` fix is applied) but is dead code. I left both in place since deleting code wasn't requested, but you may want to remove the unused ones to avoid future confusion.

---

## 📁 Root-level files

| File | Purpose |
|---|---|
| `build.gradle.kts` | Top-level Gradle build script (declares plugins used by submodules). |
| `settings.gradle.kts` | Declares included modules (`:app`) and repository sources. |
| `gradle.properties` | Global Gradle/Kotlin build flags (e.g. AndroidX, JVM args). |
| `gradle/libs.versions.toml` | Central **version catalog** — all library/plugin versions and coordinates used across the project. |
| `gradle/gradle-daemon-jvm.properties` | Pins the JVM used by the Gradle daemon. |
| `gradle/wrapper/gradle-wrapper.properties` | Gradle wrapper version/distribution URL. |
| `gradle/wrapper/gradle-wrapper.jar` | Gradle wrapper bootstrap jar. |
| `gradlew` / `gradlew.bat` | Gradle wrapper scripts (Unix/Windows) to build without a local Gradle install. |
| `local.properties` | Local machine config (Android SDK path) — not meant to be shared/committed. |
| `.gitignore` | Git ignore rules for the whole repo. |
| `README.md` | Original project README (author's own notes). |

## 📁 `app/` module config

| File | Purpose |
|---|---|
| `app/build.gradle.kts` | App module's Gradle config: applies Android/Kotlin/Hilt/KSP/Compose plugins, sets `compileSdk`/`minSdk`/`targetSdk`, and lists all dependencies (Compose, Hilt, Room, DataStore, Biometric). |
| `app/.gitignore` | Ignore rules specific to the `app` module (build outputs, etc.). |
| `app/src/main/AndroidManifest.xml` | App manifest — declares the `Application` class, `MainActivity`, launcher intent filter, and permissions. |
| `app/src/main/keepRules/rules.keep` | ProGuard/R8 keep rules (referenced if minification is enabled). |
| `app/src/main/ic_launcher-playstore.png` | Play Store listing icon asset. |

### Android resources (`app/src/main/res/`)

| File | Purpose |
|---|---|
| `res/values/colors.xml` | Named color resources. |
| `res/values/strings.xml` | App strings (e.g. app name). |
| `res/values/themes.xml` | XML-based app theme (used pre-Compose-theme / for the manifest `android:theme`). |
| `res/drawable/ic_launcher_background.xml` | Background layer of the adaptive launcher icon. |
| `res/drawable/ic_launcher_foreground.xml` | Foreground layer of the adaptive launcher icon. |
| `res/drawable/keynest_applogo.png` | App logo asset. |
| `res/mipmap-anydpi-v26/ic_launcher.xml`, `ic_launcher_round.xml` | Adaptive icon definitions (API 26+). |
| `res/mipmap-*dpi/ic_launcher*.webp` | Pre-rendered launcher icons at each density (mdpi → xxxhdpi). |
| `res/xml/backup_rules.xml` | Defines what gets included in Android auto-backup. |
| `res/xml/data_extraction_rules.xml` | Defines what gets included in device-to-device transfer / cloud backup (Android 12+). |

---

## 📁 Application entry point

| File | Purpose |
|---|---|
| `MainActivity.kt` | The single Activity (`FragmentActivity`, required for `BiometricPrompt`). Sets Compose content, creates the `NavController`, and hosts `NavGraph`. Annotated `@AndroidEntryPoint` for Hilt injection. |
| `KeyNestApplication.kt` | `Application` class annotated `@HiltAndroidApp` — bootstraps the Hilt dependency graph for the whole app. |

## 📁 `navigation/`

| File | Purpose |
|---|---|
| `Screen.kt` | Sealed class listing every navigation route (`splash`, `master_password_setup`, `password_login`, `biometric`, `home`, `create_password`, `vault`). |
| `NavGraph.kt` | Builds the `NavHost` and wires each `Screen` route to its Composable + ViewModel, defining the app's navigation flow: Splash → (Setup / Password Login / Biometric) → Home → (Create Password / Vault). |

## 📁 `di/` (Hilt dependency-injection modules)

| File | Purpose |
|---|---|
| `DataStoreModule.kt` | Provides the Preferences `DataStore` and binds `AuthPreferencesImpl` (the interface-based, `AuthMethod`-aware implementation) — **currently unused by the wired UI** (see "Duplicate code" below). |
| `DatabaseModule.kt` | Provides the singleton `KeyNestDatabase` (Room) and `PasswordDao`. |
| `RepositoryModule.kt` | Binds `AuthRepositoryImpl → AuthRepository` and `PasswordRepositoryImpl → PasswordRepository` for Hilt. |
| `SecurityModule.kt` | Provides `PasswordHasher`, `CryptoManager`, and `BiometricHelper` singletons. |

---

## 📁 `domain/` (business logic layer, framework-independent)

| File | Purpose |
|---|---|
| `model/Password.kt` | Domain model representing a saved password entry (title, encrypted bytes, IV, timestamp). |
| `model/AuthMethod.kt` | **(Added by this fix)** Enum of unlock methods: `NONE`, `PASSWORD`, `BIOMETRIC`. |
| `repository/AuthRepository.kt` | Interface: save/get auth method, save/get password hash, clear auth data. |
| `repository/PasswordRepository.kt` | Interface: insert, list, and delete saved passwords. |
| `repository/PasswordRepositoryImpl.kt` | Room-backed implementation of `PasswordRepository`, mapping between `PasswordEntity` (DB) and `Password` (domain). |
| `usecase/CreatePasswordUseCase.kt` | Use case: generate/build a new password entry. |
| `usecase/SavePasswordUseCase.kt` | Use case: persist a `Password` via the repository. |
| `usecase/GetPasswordsUseCase.kt` | Use case: stream the list of saved passwords. |
| `usecase/DeletePasswordUseCase.kt` | Use case: remove a saved password. |
| `usecase/VerifyPasswordUseCase.kt` | Use case: verify an entered master password against the stored hash. |
| `usecase/SaveAuthMethodUseCase.kt` | Use case: persist the chosen `AuthMethod`. *(unused by wired UI — see below)* |
| `usecase/GetAuthMethodUseCase.kt` | Use case: read the current `AuthMethod` as a `Flow`. *(unused by wired UI — see below)* |

---

## 📁 `data/` (data sources & implementations)

### `data/datastore/` — **unused / orphaned auth stack**
| File | Purpose |
|---|---|
| `AuthPreferences.kt` | Interface: `saveAuthMethod`/`getAuthMethod`/`savePasswordHash`/`getPasswordHash`/`clear`, keyed around the `AuthMethod` enum. |
| `AuthPreferencesImpl.kt` | DataStore-backed implementation of the above interface. |

### `data/preference/` — **actual, wired-in auth stack**
| File | Purpose |
|---|---|
| `AuthPreferences.kt` | A concrete (non-interface) `@Singleton` class storing two booleans in DataStore: `isMasterPasswordSet`, `isBiometricEnabled`. **This is the one actually injected into `SplashViewModel` and `SetupMasterPasswordViewModel` (setup package).** |

> ⚠️ Both files are literally named `AuthPreferences.kt` but live in different packages/roles — easy to confuse. See "Duplicate code" section.

### `data/repository/`
| File | Purpose |
|---|---|
| `AuthRepositoryImpl.kt` | Implements `domain.repository.AuthRepository` on top of `data.datastore.AuthPreferences`. *(part of the unused stack)* |
| `PasswordRepositoryImpl.kt` | Implements `domain.repository.PasswordRepository` on top of `PasswordDao` (Room). *(this one is used)* |

### `data/local/` (Room database)
| File | Purpose |
|---|---|
| `KeyNestDatabase.kt` | `RoomDatabase` abstract class, registers `PasswordEntity` and exposes `PasswordDao`. |
| `PasswordDao.kt` | Room DAO: insert (replace on conflict), get all passwords as `Flow`, delete by id. |
| `PasswordEntity.kt` | Room `@Entity` for the `passwords` table: id, title, encrypted password bytes, IV, createdAt. |

### `data/security/`
| File | Purpose |
|---|---|
| `CryptoManager.kt` | Wraps Android Keystore (`AES/GCM/NoPadding`) to generate/retrieve a secret key and encrypt/decrypt password bytes. |
| `PasswordHasher.kt` | SHA-256 hashing + verification helper, used for the master password. |
| `BiometricHelper.kt` | Wraps `BiometricManager` to check whether biometric/device-credential auth is available on the device. |

---

## 📁 `ui/theme/`

| File | Purpose |
|---|---|
| `Color.kt` | Color palette used by the Compose `MaterialTheme`. |
| `Type.kt` | Typography definitions (`TextStyle`s) for the theme. |
| `Theme.kt` | `KeyNestTheme` composable — assembles `Color`+`Type` (+ dynamic/dark-mode logic) into a `MaterialTheme`. |

## 📁 `Utils/Components/` — reusable "Nova" UI kit (package `infinity.developers.coreutils.Ui.Nova...`)

| File | Purpose |
|---|---|
| `Background/WhiteBackground.kt` | `NovaWhiteBackground()` — full-screen blurred white/blue gradient backdrop, used on most screens. |
| `Background/DarkTopRightBackground.kt` | Dark-themed background variant with an accent glow top-right. |
| `Background/DarkBottomLeftBackground.kt` | Dark-themed background variant with an accent glow bottom-left. |
| `Button/WhiteButton.kt` | `NovaWhiteButton` — styled primary button component. |
| `Button/BlackButton.kt` | `NovaBlackButton` — styled dark-variant button component. |
| `Card/WhiteCard.kt` | `NovaWhiteCard` — styled card container (light theme). |
| `Card/BlackCard.kt` | `NovaBlackCard` — styled card container (dark theme). |
| `Slider/NovaWhiteSlider.kt` | Styled slider, used for choosing generated-password length. |
| `TextField/NovaWhiteTextField.kt` | Styled text field (light theme). |
| `TextField/NovaBlackTextField.kt` | Styled text field (dark theme). |
| `Toggle/NovaWhiteToggle.kt` | Styled switch/toggle (light theme). |
| `Toggle/NovaBlackToggle.kt` | Styled switch/toggle (dark theme). |

---

## 📁 `ui/screen/` — feature screens

### `splash/`
| File | Purpose |
|---|---|
| `SplashScreen.kt` | First screen shown; observes `SplashViewModel.destination` and navigates to setup/biometric/password-login accordingly. |
| `SplashViewModel.kt` | Combines `isMasterPasswordSet` + `isBiometricEnabled` (from `data.preference.AuthPreferences`) into a `SplashDestination`. |

### `setup/` — **the master-password setup flow actually used by `NavGraph`**
| File | Purpose |
|---|---|
| `SetupMasterPasswordScreen.kt` | Composable form: enter + confirm master password, optionally enable biometrics (with a `BiometricPrompt` enrollment step), then calls `viewModel.completeSetup(...)`. |
| `SetupMasterPasswordViewModel.kt` | Validates the password (non-empty, length ≥ 6, matches confirmation) and saves the result via `data.preference.AuthPreferences`. |
| `AuthCard.kt` | **(Added by this fix)** Selectable icon+label card composable, used by `BiometricScreen` to let the user choose "Biometric" vs "Password". |

### `masterpassword/` — **duplicate/orphaned variant, not referenced by `NavGraph`**
| File | Purpose |
|---|---|
| `SetupMasterPasswordScreen.kt` | An alternate version of the setup screen (same idea, slightly different composition). Not routed to anywhere. |
| `SetupMasterPasswordViewModel.kt` | Alternate ViewModel with an extra `BiometricVerificationRequired` state and separate `MutableStateFlow` fields for password input. Not used. |

### `passwordlogin/`
| File | Purpose |
|---|---|
| `PasswordLoginScreen.kt` | Composable: master-password re-entry screen for returning users who don't use biometrics. |
| `PasswordViewModel.kt` | Contains `PasswordLoginViewModel` — validates the entered password (currently a stubbed `TODO`, always accepts). |

### `biometric/`
| File | Purpose |
|---|---|
| `BiometricScreen.kt` | Shows the biometric unlock screen, auto-triggers `BiometricPrompt` on launch, and falls back to password login on error/unavailability. Uses the new `AuthCard`. |
| `BiometricViewModel.kt` | Holds `BiometricAuthState` (`Idle`/`PromptReady`/`Authenticated`/`Unavailable`/`Error`) and exposes availability/trigger/result handlers backed by `BiometricHelper`. |

### `homescreen/`
| File | Purpose |
|---|---|
| `HomeScreen.kt` | Main dashboard after unlocking — entry points to "Create Password" and "Vault". |

### `createpassword/` — password generator UI + support types
| File | Purpose |
|---|---|
| `CreatePassword.kt` | An alternate/earlier `CreatePasswordScreen` composable (enhanced UI version) — **not the one routed by `NavGraph`** (see `logincreatepassword/` below). |
| `CreatePasswordUiState.kt` | UI state data class for the password-creation screen (title, generated password, length, difficulty). |
| `Difficulty.kt` | Enum of password-generation difficulty/complexity levels. |
| `PasswordGenerator.kt` | Pure logic for generating a random password string given length + `Difficulty`. |

### `logincreatepassword/` — **the create-password flow actually wired by `NavGraph`**
| File | Purpose |
|---|---|
| `CreatePasswordScreen.kt` | Composable form: title input, generated password display, difficulty selector, length slider, generate/copy/save actions. |
| `CreatePasswordViewModel.kt` | Holds `CreatePasswordUiState`, calls `PasswordGenerator`, and saves entries via `SavePasswordUseCase`. |

### `vault/`
| File | Purpose |
|---|---|
| `VaultScreen.kt` | Lists saved passwords with a search bar; copy/delete actions per entry. |
| `VaultUiState.kt` | UI state data class for the vault screen (search text, password list). |
| `VaultViewModel.kt` | Loads passwords via `GetPasswordsUseCase`, filters by search, and exposes copy/delete actions. |
| `PasswordCard.kt` | Composable row/card representing a single saved password entry in the vault list. |

---

## 📁 Tests

| File | Purpose |
|---|---|
| `app/src/test/java/.../ExampleUnitTest.kt` | Default JVM unit test template generated by Android Studio (not project-specific logic yet). |
| `app/src/androidTest/java/.../ExampleInstrumentedTest.kt` | Default instrumented (on-device) test template generated by Android Studio. |

---

## ⚠️ Duplicate / Unused Code (worth cleaning up)

The project has **two parallel implementations** for two different features. Only one side of each pair is wired into `NavGraph.kt` / Hilt's actual object graph; the other compiles but is dead code:

1. **Auth preferences**
    - **Used:** `data/preference/AuthPreferences.kt` (simple boolean flags, injected via constructor `@Inject` directly, no module needed).
    - **Unused:** `data/datastore/AuthPreferences.kt` + `AuthPreferencesImpl.kt` + `domain/repository/AuthRepository.kt` + `data/repository/AuthRepositoryImpl.kt` + `SaveAuthMethodUseCase.kt` + `GetAuthMethodUseCase.kt` + `di/DataStoreModule.kt`/`RepositoryModule.kt`'s `AuthRepository` binding — this is the fuller, `AuthMethod`-based design, but nothing in the UI currently calls it.

2. **Master password setup screen**
    - **Used:** `ui/screen/setup/SetupMasterPasswordScreen.kt` + `SetupMasterPasswordViewModel.kt` (referenced by `NavGraph.kt`).
    - **Unused:** `ui/screen/masterpassword/SetupMasterPasswordScreen.kt` + `SetupMasterPasswordViewModel.kt` (not referenced anywhere).

3. **Create-password screen**
    - **Used:** `ui/screen/logincreatepassword/CreatePasswordScreen.kt` + `CreatePasswordViewModel.kt` (referenced by `NavGraph.kt`).
    - **Unused:** `ui/screen/createpassword/CreatePassword.kt` (an alternate `CreatePasswordScreen`, not routed) — though its sibling files `CreatePasswordUiState.kt`, `Difficulty.kt`, and `PasswordGenerator.kt` **are** used by the active `logincreatepassword` ViewModel.

None of these break the build once the three fixes above are applied, but if you intend to finish the `AuthMethod`-based auth design (which is more complete — it distinguishes `NONE`/`PASSWORD`/`BIOMETRIC` rather than just two booleans), you'll want to switch `SplashViewModel`/`SetupMasterPasswordViewModel` over to use `domain.repository.AuthRepository` instead of `data.preference.AuthPreferences`, and delete the orphaned duplicates.
