package infinity.developers.coreutils.Ui.Nova.Components.Background

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun NovaWhiteBackground() {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .blur(300.dp)
            .background(Color.White)
    ) {

        Canvas(
            modifier = Modifier.fillMaxSize()
        ) {
            val circleRadius = size.width * 1.2f

            drawCircle(
                color = Color(0xFF2196F3),
                radius = circleRadius,
                center = Offset(
                    x = size.width / 2,
                    y = -circleRadius / 4
                )
            )
        }
    }
}