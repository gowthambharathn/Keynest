package skynetbee.gowtham.keynest.Utils

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


/**
 * Created by: Gowtham Bharath N
 * Created on: 10/6/2026
 */

@Composable
fun titleColor(): Color {
    return if (isSystemInDarkTheme()) {
        Color(0xFFFFFFFF)
    } else {
        Color(0xFF363636)
    }
}