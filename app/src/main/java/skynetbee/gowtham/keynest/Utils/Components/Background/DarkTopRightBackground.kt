package infinity.developers.coreutils.Ui.Nova.Components.Background

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun NovaTopRightBackground() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .blur(300.dp) // 🔥 Blur entire screen (adjust value)
            .background(Color(0xFF0D47A1))
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .drawBehind {

                    val radius = size.maxDimension * 1.1f

                    val center = Offset(
                        x = size.width * -1f,
                        y = size.height * 0.85f
                    )

                    drawCircle(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                Color(0xFF131313),
                                Color(0xFF000000)
                            ),
                            center = center,
                            radius = radius
                        ),
                        radius = radius,
                        center = center
                    )
                }
        )
    }
}
