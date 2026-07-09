package skynetbee.gowtham.keynest.auth.createpassword

/**
 * Created by Gowtham Barath
 * Date: 05-07-2026
 */

data class CreatePasswordUiState(

    val title: String = "",

    val difficulty: Difficulty = Difficulty.MEDIUM,

    val length: Float = 16f,

    val generatedPassword: String = "",

    val isLoading: Boolean = false
)