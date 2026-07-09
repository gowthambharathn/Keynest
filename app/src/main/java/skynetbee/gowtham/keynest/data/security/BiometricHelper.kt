package skynetbee.gowtham.keynest.data.security


/**
 * Created by Gowtham Barath
 * Date: 23-06-2026
 */
import android.content.Context
import androidx.biometric.BiometricManager
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class BiometricHelper @Inject constructor(
    @ApplicationContext private val context: Context
) {

    fun isBiometricAvailable(): Boolean {
        return when (
            BiometricManager.from(context).canAuthenticate(
                BiometricManager.Authenticators.BIOMETRIC_STRONG
                        or BiometricManager.Authenticators.DEVICE_CREDENTIAL
            )
        ) {
            BiometricManager.BIOMETRIC_SUCCESS -> true
            else -> false
        }
    }
}