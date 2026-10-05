package skynetbee.gowtham.keynest.Utils

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Custom animated loading indicator for the Nova Design System.
 *
 * @param modifier Modifier for styling and layout sizing.
 * @param size Outer dimension size of the loader.
 * @param strokeWidth Thickness of the loading arc ring.
 * @param primaryColor Main brand accent color for the sweep gradient.
 * @param secondaryColor Secondary accent color for gradient transitions.
 */
@Composable
fun NovaLoadingIndicator(
    modifier: Modifier = Modifier,
    size: Dp = 48.dp,
    strokeWidth: Dp = 4.dp,
    primaryColor: Color = Color(0xFF2196F3),
    secondaryColor: Color = Color(0xFF00BCD4)
) {
    val infiniteTransition = rememberInfiniteTransition(label = "NovaLoadingTransition")

    // Continuous 360-degree rotation angle
    val rotationAngle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1100, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "NovaRotationAngle"
    )

    // Pulsing sweep angle for smooth expansion/contraction
    val sweepAngle by infiniteTransition.animateFloat(
        initialValue = 30f,
        targetValue = 270f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 900, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "NovaSweepAngle"
    )

    Box(
        modifier = modifier.size(size),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.size(size)) {
            val strokePx = strokeWidth.toPx()
            val canvasSize = size.toPx()
            val diameter = canvasSize - strokePx

            val gradientBrush = Brush.sweepGradient(
                colors = listOf(
                    primaryColor.copy(alpha = 0.1f),
                    secondaryColor,
                    primaryColor
                )
            )

            // Background track ring
            drawArc(
                color = primaryColor.copy(alpha = 0.12f),
                startAngle = 0f,
                sweepAngle = 360f,
                useCenter = false,
                style = Stroke(width = strokePx)
            )

            // Rotating active gradient arc
            rotate(degrees = rotationAngle) {
                drawArc(
                    brush = gradientBrush,
                    startAngle = 0f,
                    sweepAngle = sweepAngle,
                    useCenter = false,
                    style = Stroke(
                        width = strokePx,
                        cap = StrokeCap.Round
                    )
                )
            }
        }
    }
}