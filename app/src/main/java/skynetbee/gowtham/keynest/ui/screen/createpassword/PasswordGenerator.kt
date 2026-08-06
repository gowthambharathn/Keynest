package skynetbee.gowtham.keynest.ui.screen.createpassword

import java.security.SecureRandom

/**
 * Created by Gowtham Barath
 * Date: 05-07-2026
 */

object PasswordGenerator {

    private val random = SecureRandom()

    private const val LOWERCASE =
        "abcdefghijklmnopqrstuvwxyz"

    private const val UPPERCASE =
        "ABCDEFGHIJKLMNOPQRSTUVWXYZ"

    private const val NUMBERS =
        "0123456789"

    private const val SYMBOLS =
        "!@#$%^&*()-_=+[]{}<>?/|"

    fun generate(
        length: Int,
        difficulty: Difficulty
    ): String {

        require(length in 1..100) {
            "Password length must be between 1 and 100."
        }

        val characters = when (difficulty) {

            Difficulty.EASY ->
                LOWERCASE + UPPERCASE

            Difficulty.MEDIUM ->
                LOWERCASE + UPPERCASE + NUMBERS

            Difficulty.HARD ->
                LOWERCASE + UPPERCASE + NUMBERS + SYMBOLS
        }

        return buildString {
            repeat(length) {
                append(
                    characters[random.nextInt(characters.length)]
                )
            }
        }
    }
}